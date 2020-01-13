package ru.coderedwolf.mvi.core

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import ru.coderedwolf.mvi.core.TestAction.*
import ru.coderedwolf.mvi.core.TestEffect.*
import ru.coderedwolf.mvi.core.elements.Bootstrapper
import ru.coderedwolf.mvi.core.elements.Middleware
import ru.coderedwolf.mvi.core.elements.Navigator
import ru.coderedwolf.mvi.core.elements.Reducer
import java.util.concurrent.TimeUnit

/**
 * @author CodeRedWolf.
 */

const val INITIAL_COUNTER = 10
const val INITIAL_LOADING = false

const val DELAYED_FULFILL_AMOUNT = 5

const val CONDITIONAL_MULTIPLIER = 10

data class TestState(
    val id: Long = 1L,
    val counter: Int = INITIAL_COUNTER,
    val loading: Boolean = INITIAL_LOADING
)

sealed class TestAction {
    object Unfulfillable : TestAction()
    object FulfillableInstantly : TestAction()
    data class FulfillableAsync(val delayMs: Long) : TestAction()
    object TranslatesTo3Effects : TestAction()
    object MaybeFulfillable : TestAction()
}

sealed class TestViewEvent

sealed class TestEffect {
    data class InstantEffect(val amount: Int) : TestEffect()
    object StartedAsync : TestEffect()
    data class FinishedAsync(val amount: Int) : TestEffect()
    object MultipleEffect1 : TestEffect()
    object MultipleEffect2 : TestEffect()
    object MultipleEffect3 : TestEffect()
    data class ConditionalThingHappened(val multiplier: Int) : TestEffect()
}

class TestReducer : Reducer<TestState, TestEffect> {

    override fun invoke(state: TestState, effect: TestEffect): TestState = when (effect) {
        is InstantEffect -> state.copy(counter = state.counter + effect.amount)
        StartedAsync -> state.copy(loading = true)
        is FinishedAsync -> state.copy(counter = state.counter + effect.amount, loading = false)
        MultipleEffect1,
        MultipleEffect2,
        MultipleEffect3 -> state.copy(counter = state.counter + 1)
        is ConditionalThingHappened -> state.copy(counter = state.counter * effect.multiplier)
    }
}

class TestMiddleware(
    private val asyncWorkScheduler: Scheduler
) : Middleware<TestAction, TestState, TestEffect> {

    override fun invoke(action: TestAction, state: TestState): Flowable<TestEffect> = when (action) {
        Unfulfillable -> Flowable.empty()
        FulfillableInstantly -> Flowable.just(InstantEffect(1))
        is FulfillableAsync -> Flowable.just(DELAYED_FULFILL_AMOUNT)
            .delay(action.delayMs, TimeUnit.MILLISECONDS, asyncWorkScheduler)
            .map<TestEffect>(::FinishedAsync)
            .startWith(StartedAsync)
        TranslatesTo3Effects -> Flowable.just(
            MultipleEffect1,
            MultipleEffect2,
            MultipleEffect3
        )
        MaybeFulfillable ->
            if (state.counter % 3 == 0) Flowable.just<TestEffect>(ConditionalThingHappened(CONDITIONAL_MULTIPLIER))
            else Flowable.empty<TestEffect>()
    }
}

class TestBootstrapper : Bootstrapper<TestAction> {

    override fun invoke(): Observable<TestAction> {
        return Observable.empty()
    }
}

class TestNavigator : Navigator<TestState, TestEffect> {

    override fun invoke(state: TestState, effect: TestEffect) {

    }
}

class TestView(
    testActionSubject: PublishSubject<TestAction>,
    private val testStateObserver: TestObserver<TestState>
) : MviView<TestAction, TestState, TestViewEvent> {

    override val actions: Observable<TestAction> = testActionSubject.hide()

    override fun render(state: TestState) {
        testStateObserver.onNext(state)
    }
}