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
abstract class BaseViewModelStore<Action, State, Effect, ViewEvent>(
    initialState: State,
    bootstrapper: Bootstrapper<Action>? = null,
    private val schedulerProvider: SchedulerProvider,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val viewEventProducer: ViewEventProducer<State, Effect, ViewEvent>? = null,
    private val navigator: Navigator<State, Effect>? = null
) : ViewModel(), Store<Action, State, ViewEvent> {

    private val wiring = CompositeDisposable()
    private var viewBind: Disposable? = null

    private val stateSubject = BehaviorSubject.createDefault(initialState)
    private val actionSubject = PublishSubject.create<Action>()
    private val effectSubject = PublishSubject.create<Effect>()
    private val viewEventSubject = PublishSubject.create<ViewEvent>()

    private val stateEffectPairSubject = PublishSubject.create<Pair<State, Effect>>()

    init {
        effectSubject
            .withLatestFrom(stateSubject)
            .observeOn(schedulerProvider.single)
            .map { (effect, state) -> reduceInvoke(state, effect) }
            .distinctUntilChanged()
            .subscribe(stateSubject::onNext)
            .addTo(wiring)

        actionSubject
            .withLatestFrom(stateSubject)
            .flatMap { (action, state) -> middleware.invoke(action, state) }
            .subscribe(effectSubject::onNext)
            .addTo(wiring)

        stateEffectPairSubject
            .observeOn(schedulerProvider.mainThread)
            .subscribe { (state, effect) -> navigator?.invoke(state, effect) }
            .addTo(wiring)

        bootstrapper
            ?.invoke()
            ?.subscribe(actionSubject::onNext)
            ?.addTo(wiring)
    }

    @CallSuper
    override fun bindView(mviView: MviView<Action, State, ViewEvent>) {
        val disposable = CompositeDisposable()

        viewEventSubject
            .observeOn(schedulerProvider.mainThread)
            .subscribe(mviView::route)
            .addTo(disposable)

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

    @CallSuper
    override fun onCleared() = wiring.dispose()

    private fun reduceInvoke(
        state: State,
        effect: Effect
    ): State = reducer
        .invoke(state, effect)
        .also { newState ->
            postChangedState(newState, effect)
            viewEventProducerInvoke(newState, effect)
        }

    private fun postChangedState(
        newState: State,
        effect: Effect
    ) = stateEffectPairSubject.onNext(newState to effect)

    private fun viewEventProducerInvoke(
        newState: State,
        effect: Effect
    ) = viewEventProducer
        ?.invoke(newState, effect)
        ?.let(viewEventSubject::onNext)
}