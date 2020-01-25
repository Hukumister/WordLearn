package ru.coderedwolf.mvi.core

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.Flowables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.withLatestFrom
import org.reactivestreams.Publisher
import ru.coderedwolf.mvi.core.elements.EventProducer
import ru.coderedwolf.mvi.core.elements.Middleware
import ru.coderedwolf.mvi.core.elements.Reducer

/**
 * @author HaronCode.
 */
abstract class AbstractStore<Action : Any, State : Any, Event : Any, Effect : Any>(
    initialState: State,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val eventProducer: EventProducer<State, Effect, Event>? = null
) : Store<Action, State, Event> {

    private val compositeDisposable = CompositeDisposable()

    private val stateProcessor = BehaviorProcessor.createDefault(initialState)
    private val viewStateProcessor = BehaviorProcessor.create<State>()

    private val actionProcessor = PublishProcessor.create<Action>()
    private val effectProcessor = PublishProcessor.create<Effect>()
    private val eventProcessor = BehaviorProcessor.create<Event>()

    private val stateEffectProcessor = PublishProcessor.create<Pair<State, Effect>>()

    init {
        compositeDisposable += Flowables.zip(
            stateProcessor,
            effectProcessor
        )
            .scan(initialState) { _, (state, effect) -> reducer.invoke(state, effect) }
            .subscribe(stateProcessor::onNext, ::onError)

        compositeDisposable += actionProcessor
            .doOnNext(::onAction)
            .withLatestFrom(stateProcessor)
            .flatMap { (action, state) -> middleware.invoke(action, state) }
            .subscribe(effectProcessor::onNext, ::onError)

        compositeDisposable += Flowables.zip(
            stateProcessor.skip(1),
            effectProcessor
        )
            .flatMap { (state, effect) -> produceEventFlowable(state, effect) }
            .subscribe(eventProcessor::onNext, ::onError)
    }

    override val eventSource: Publisher<Event>
        get() = eventProcessor.hide()

    override val stateSource: Publisher<State>
        get() = stateProcessor
            .distinctUntilChanged()
            .hide()

    protected val effectSource: Flowable<Effect>
        get() = effectProcessor.hide()

    override fun accept(action: Action) {
        actionProcessor.offer(action)
    }

    override fun isDisposed() = compositeDisposable.isDisposed

    override fun dispose() = compositeDisposable.dispose()

    private fun produceEventFlowable(
        state: State,
        effect: Effect
    ): Flowable<Event> = eventProducer?.invoke(state, effect)
        ?.let { event -> Flowable.just(event) }
        ?: Flowable.empty<Event>()

    protected open fun onAction(action: Action) = Unit

    protected open fun onError(throwable: Throwable) = Unit

}