package ru.coderedwolf.mvi.core

import io.reactivex.Flowable
import io.reactivex.Scheduler
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
 * @author CodeRedWolf.
 */
abstract class AbstractStore<Action : Any, State : Any, Event : Any, Effect : Any>(
    initialState: State,
    mainScheduler: Scheduler,
    reducerScheduler: Scheduler,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val eventProducer: EventProducer<State, Effect, Event>? = null
) : Store<Action, State, Event> {

    private val stateProcessor = BehaviorProcessor.createDefault(initialState)
    private val viewStateProcessor = BehaviorProcessor.create<State>()

    private val actionProcessor = PublishProcessor.create<Action>()
    private val effectProcessor = PublishProcessor.create<Effect>()

    private val eventProcessor = BehaviorProcessor.create<Event>()

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable += Flowables.zip(stateProcessor, effectProcessor)
            .observeOn(reducerScheduler)
            .map { (state, effect) -> reducer(state, effect) }
            .subscribe(stateProcessor::onNext)

        compositeDisposable += actionProcessor
            .withLatestFrom(stateProcessor)
            .flatMap { (action, state) -> middleware(action, state) }
            .subscribe(effectProcessor::onNext)

        val stateWithoutInitProcessor = stateProcessor
            .skip(1)

        compositeDisposable += Flowables.zip(stateWithoutInitProcessor, effectProcessor)
            .flatMap { (state, effect) -> produceEventFlowable(state, effect) }
            .subscribe(eventProcessor::onNext)

        compositeDisposable += stateProcessor
            .distinctUntilChanged()
            .observeOn(mainScheduler)
            .subscribe(viewStateProcessor::onNext)
    }

    override val eventSource: Publisher<Event>
        get() = eventProcessor.hide()

    override val stateSource: Publisher<State>
        get() = stateProcessor.hide()

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

//    override fun add(disposable: Disposable): Boolean = compositeDisposable.add(disposable)
//
//    override fun remove(disposable: Disposable): Boolean = compositeDisposable.remove(disposable)
//
//    override fun delete(disposable: Disposable): Boolean = compositeDisposable.delete(disposable)

    fun init() {


//        stateEffectPairProcessor
//            .observeOn(mainScheduler)
//            .subscribe { (state, effect) -> navigator?.invoke(state, effect) }
//            .addTo(compositeDisposable)

//        bootstrapper
//            ?.invoke()
//            ?.subscribe(actionProcessor::onNext)
//            ?.addTo(wiring)
    }

    //
//    override fun destroy() = wiring.dispose()
//
//    override fun bindView(mviView: MviView<Action, State, Event>) {
//        check(!wiring.isDisposed) { "Attempt to bind view after the store was destroyed" }
//        check(viewBind.size() == 0) { "View bind didn't dispose last time" }
//
//        eventProcessor
//            .observeOn(mainScheduler)
//            .subscribe(mviView::route)
//            .addTo(viewBind)
//
//        stateProcessor
//            .distinctUntilChanged()
//            .observeOn(mainScheduler)
//            .subscribe(mviView::render)
//            .addTo(viewBind)
//
//        mviView.actions
//            .subscribe(actionProcessor::onNext)
//            .addTo(viewBind)
//    }
//
//    override fun unbindView() {
//        viewBind.clear()
//    }
//
//    private fun reduceInvoke(
//        state: State,
//        effect: Effect
//    ): State = reducer
//        .invoke(state, effect)
//        .also { newState ->
//            postChangedState(newState, effect)
////            viewEventProducerInvoke(newState, effect)
//        }

//    private fun viewEventProducerInvoke(
//        newState: State,
//        effect: Effect
//    ) = eventProducer
//        ?.invoke(newState, effect)
//        ?.let(eventProcessor::onNext)
}