package ru.coderedwolf.mvi.core

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import ru.coderedwolf.mvi.core.elements.*

/**
 * @author HaronCode.
 */
open class BaseStore<Action, State, Event, Effect>(
    initialState: State,
    private val mainScheduler: Scheduler,
    private val reducerScheduler: Scheduler,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val bootstrapper: Bootstrapper<Action>? = null,
    private val eventProducer: EventProducer<State, Effect, Event>? = null,
    private val navigator: Navigator<State, Effect>? = null
) : LifecycleStore<Action, State, Event> {

    private val viewBind = CompositeDisposable()
    private val wiring = CompositeDisposable(viewBind)

    private val stateProcessor = BehaviorProcessor.createDefault(initialState)
    private val actionProcessor = PublishProcessor.create<Action>()
    private val effectProcessor = PublishProcessor.create<Effect>()
    private val eventProcessor = PublishProcessor.create<Event>()

    private val stateEffectPairProcessor = PublishProcessor.create<Pair<State, Effect>>()

    override fun create() {
        Flowables.zip(
            stateProcessor,
            effectProcessor
        )
            .observeOn(reducerScheduler)
            .map { (state, effect) -> reduceInvoke(state, effect) }
            .subscribe(stateProcessor::onNext)
            .addTo(wiring)

        actionProcessor
            .withLatestFrom(stateProcessor)
            .flatMap { (action, state) -> middleware.invoke(action, state) }
            .subscribe(effectProcessor::onNext)
            .addTo(wiring)

        stateEffectPairProcessor
            .observeOn(mainScheduler)
            .subscribe { (state, effect) -> navigator?.invoke(state, effect) }
            .addTo(wiring)

        bootstrapper
            ?.invoke()
            ?.subscribe(actionProcessor::onNext)
            ?.addTo(wiring)
    }

    override fun destroy() = wiring.dispose()

    override fun bindView(mviView: MviView<Action, State, Event>) {
        check(!wiring.isDisposed) { "Attempt to bind view after the store was destroyed" }
        check(viewBind.size() == 0) { "View bind didn't dispose last time" }

        eventProcessor
            .observeOn(mainScheduler)
            .subscribe(mviView::route)
            .addTo(viewBind)

        stateProcessor
            .distinctUntilChanged()
            .observeOn(mainScheduler)
            .subscribe(mviView::render)
            .addTo(viewBind)

        mviView.actions
            .subscribe(actionProcessor::onNext)
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
    ) = stateEffectPairProcessor.onNext(newState to effect)

    private fun viewEventProducerInvoke(
        newState: State,
        effect: Effect
    ) = eventProducer
        ?.invoke(newState, effect)
        ?.let(eventProcessor::onNext)
}