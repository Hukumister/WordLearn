package ru.coderedwolf.mvi.core

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject
import ru.coderedwolf.mvi.core.elements.*

/**
 * @author CodeRedWolf.
 */
open class BaseStore<Action, State, ViewEvent, Effect>(
    initialState: State,
    private val mainScheduler: Scheduler,
    private val singleScheduler: Scheduler,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val bootstrapper: Bootstrapper<Action>? = null,
    private val viewEventProducer: ViewEventProducer<State, Effect, ViewEvent>? = null,
    private val navigator: Navigator<State, Effect>? = null
) : Store<Action, State, ViewEvent> {

    private val viewBind = CompositeDisposable()
    private val wiring = CompositeDisposable(viewBind)

    private val stateSubject = BehaviorProcessor.createDefault(initialState)
    private val actionSubject = PublishProcessor.create<Action>()
    private val effectSubject = PublishProcessor.create<Effect>()
    private val viewEventSubject = PublishProcessor.create<ViewEvent>()

    private val stateEffectPairSubject = PublishSubject.create<Pair<State, Effect>>()

    fun initStore() {
        Flowables.zip(
            effectSubject,
            stateSubject
        )
            .observeOn(singleScheduler)
            .map { (effect, state) -> reduceInvoke(state, effect) }
            .subscribe(stateSubject::onNext)
            .addTo(wiring)

        actionSubject
            .withLatestFrom(stateSubject)
            .flatMap { (action, state) -> middleware.invoke(action, state) }
            .subscribe(effectSubject::onNext)
            .addTo(wiring)

        stateEffectPairSubject
            .observeOn(mainScheduler)
            .subscribe { (state, effect) -> navigator?.invoke(state, effect) }
            .addTo(wiring)

        bootstrapper
            ?.invoke()
            ?.subscribe(actionSubject::onNext)
            ?.addTo(wiring)
    }

    fun destroyStore() = wiring.dispose()

    override fun bindView(mviView: MviView<Action, State, ViewEvent>) {
        check(!wiring.isDisposed) { "Attempt to bind view after the store was destroyed" }
        check(viewBind.size() == 0) { "View bind didn't dispose last time" }

        viewEventSubject
            .observeOn(mainScheduler)
            .subscribe(mviView::route)
            .addTo(viewBind)

        stateSubject
            .distinctUntilChanged()
            .observeOn(mainScheduler)
            .subscribe(mviView::render)
            .addTo(viewBind)

        mviView.actions
            .subscribe(actionSubject::onNext)
            .addTo(viewBind)
    }

    override fun unbindView() {
        viewBind.clear()
    }

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