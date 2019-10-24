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
abstract class BaseViewModelStore<Action, State, Effect, NavigationEvent>(
    protected val schedulerProvider: SchedulerProvider,
    initialState: State,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val navigator: Navigator<State, Effect, NavigationEvent>? = null,
    private val bootstrapper: Bootstrapper<Action>? = null
) : ViewModel(), Store<Action, State> {

    private val wiring = CompositeDisposable()
    private var viewBind: Disposable? = null

    private val stateSubject = BehaviorSubject.createDefault(initialState)
    private val actionSubject = PublishSubject.create<Action>()
    private val effectSubject = PublishSubject.create<Effect>()
    private val navigationEventSubject = PublishSubject.create<NavigationEvent>()

    init {
        effectSubject
            .withLatestFrom(stateSubject)
            .observeOn(schedulerProvider.single)
            .map { (effect, state) ->
                val newState = reducer.invoke(state, effect)
                navigator?.invoke(newState, effect)?.let(navigationEventSubject::onNext)
                newState
            }
            .distinctUntilChanged()
            .subscribe(stateSubject::onNext)
            .addTo(wiring)

        actionSubject
            .withLatestFrom(stateSubject)
            .flatMap { (action, state) -> middleware.invoke(action, state) }
            .subscribe(effectSubject::onNext)
            .addTo(wiring)

        navigationEventSubject
            .observeOn(schedulerProvider.mainThread)
            .subscribe(::onNavigationEvent)
            .addTo(wiring)

        bootstrapper
            ?.invoke()
            ?.subscribe(actionSubject::onNext)
            ?.addTo(wiring)
    }

    @CallSuper
    override fun bindView(mviView: MviView<Action, State>) {
        val disposable = CompositeDisposable()

        stateSubject
            .observeOn(schedulerProvider.mainThread)
            .subscribe(mviView::render)
            .addTo(disposable)

        mviView.actions
            .subscribe(actionSubject::onNext)
            .addTo(disposable)

        viewBind = disposable
    }

    @CallSuper
    override fun unbindView() = viewBind?.dispose() ?: Unit

    open fun onNavigationEvent(event: NavigationEvent) = Unit

    @CallSuper
    override fun onCleared() = wiring.dispose()
}