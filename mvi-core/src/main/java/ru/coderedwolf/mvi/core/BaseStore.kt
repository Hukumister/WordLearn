package ru.coderedwolf.mvi.core

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
abstract class BaseStore<Action, State, Effect, Event>(
    initialState: State,
    private val reducer: Reducer<State, Effect>,
    private val schedulerProvider: SchedulerProvider,
    private val navigator: Navigator<State, Effect, Event>,
    private val middleWare: MiddleWare<Action, State, Effect>
) : ViewModel(), Store<Action, State> {

    private val wiring = CompositeDisposable()
    private var viewBind: Disposable? = null

    private val state = BehaviorSubject.createDefault(initialState)
    private val actions = PublishSubject.create<Action>()
    private val effect = PublishSubject.create<Effect>()
    private val events = PublishSubject.create<Event>()

    init {
        initStore()
    }

    @CallSuper
    override fun bindView(mviView: MviView<Action, State>) {
        val disposable = CompositeDisposable()
        state
            .observeOn(schedulerProvider.mainThread)
            .subscribe(mviView::render)
            .addTo(disposable)

        mviView.actions
            .subscribe(actions::onNext)
            .addTo(disposable)

        viewBind = disposable
    }

    @CallSuper
    override fun unBindView() = viewBind?.dispose() ?: Unit

    abstract fun onNavigationEvent(event: Event)

    @CallSuper
    override fun onCleared() = wiring.dispose()

    private fun initStore() {
        effect
            .withLatestFrom(state)
            .observeOn(schedulerProvider.single)
            .map { (effect, state) ->
                val newState =  reducer.reduce(state, effect)
                navigator.handle(newState, effect)?.let(events::onNext)
                newState
            }
            .distinctUntilChanged()
            .subscribe(state::onNext)
            .addTo(wiring)

        actions
            .withLatestFrom(state)
            .flatMap { (action, state) -> middleWare.handle(action, state) }
            .subscribe(effect::onNext)
            .addTo(wiring)

        events
            .observeOn(schedulerProvider.mainThread)
            .subscribe(::onNavigationEvent)
            .addTo(wiring)
    }

}