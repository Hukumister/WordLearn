package ru.coderedwolf.mvi.core.impl

import android.os.Looper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import ru.coderedwolf.mvi.core.MviView
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.mvi.core.elements.*
import ru.coderedwolf.mvi.core.schedule.SchedulerProvider

/**
 * @author CodeRedWolf.
 */
class BaseStore<Action, State, ViewEvent, Effect>(
    initialState: State,
    private val mainScheduler: SchedulerProvider,
    private val singleScheduler: SchedulerProvider,
    private val reducer: Reducer<State, Effect>,
    private val middleware: Middleware<Action, State, Effect>,
    private val bootstrapper: Bootstrapper<Action>? = null,
    private val viewEventProducer: ViewEventProducer<State, Effect, ViewEvent>? = null,
    private val navigator: Navigator<State, Effect>? = null
) : Store<Action, State, ViewEvent> {

    private val wiring = CompositeDisposable()
    private var viewBind: Disposable? = null

    private val stateSubject = BehaviorSubject.createDefault(initialState)
    private val actionSubject = PublishSubject.create<Action>()
    private val effectSubject = PublishSubject.create<Effect>()
    private val viewEventSubject = PublishSubject.create<ViewEvent>()

    private val stateEffectPairSubject = PublishSubject.create<Pair<State, Effect>>()

    fun initStore() {
        effectSubject
            .withLatestFrom(stateSubject)
            .observeOn(singleScheduler.invoke())
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
            .observeOn(mainScheduler.invoke())
            .subscribe { (state, effect) -> navigator?.invoke(state, effect) }
            .addTo(wiring)

        bootstrapper
            ?.invoke()
            ?.subscribe(actionSubject::onNext)
            ?.addTo(wiring)
    }

    fun destroyStore() = wiring.dispose()

    override fun bindView(mviView: MviView<Action, State, ViewEvent>) {
        check(Looper.myLooper() == Looper.getMainLooper()) { "bindView must be called only from main thread" }
        check(viewBind?.isDisposed ?: true) { "View bind didn't dispose last time" }

        val disposable = CompositeDisposable()

        viewEventSubject
            .observeOn(mainScheduler.invoke())
            .subscribe(mviView::route)
            .addTo(disposable)

        stateSubject
            .observeOn(mainScheduler.invoke())
            .subscribe(mviView::render)
            .addTo(disposable)

        mviView.actions
            .subscribe(actionSubject::onNext)
            .addTo(disposable)

        viewBind = disposable
    }

    override fun unbindView() {
        check(Looper.myLooper() == Looper.getMainLooper()) { "unbindView must be called only from main thread" }
        viewBind?.dispose()
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