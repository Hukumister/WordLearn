package ru.haroncode.mvi.core

import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.haroncode.mvi.core.TestAction.*
import java.util.concurrent.TimeUnit

/**
 * @author HaronCode.
 */
class BaseStoreTest {

    private lateinit var baseStore: BaseStore<TestAction, TestState, TestViewEvent, TestEffect>
    private lateinit var states: TestObserver<TestState>
    private lateinit var actions: PublishSubject<TestAction>
    private lateinit var mainScheduler: TestScheduler
    private lateinit var reducerScheduler: TestScheduler
    private lateinit var asyncWorkScheduler: TestScheduler
    private lateinit var view: TestView

    @Before
    fun prepare() {

        mainScheduler = TestScheduler()
        reducerScheduler = TestScheduler()
        asyncWorkScheduler = TestScheduler()

        baseStore = BaseStore(
            initialState = TestState(),
            reducerScheduler = reducerScheduler,
            mainScheduler = mainScheduler,
            reducer = TestReducer(),
            middleware = TestMiddleware(asyncWorkScheduler),
            navigator = TestNavigator()
        )
        baseStore.create()

        actions = PublishSubject.create()

        states = TestObserver()

        view = TestView(actions, states)
        baseStore.bindView(view)

        mainScheduler.triggerActions()
    }

    @Test
    fun `if there are no actions, feature only emits initial state`() {
        assertEquals(1, states.onNextEvents().size)
    }

    @Test
    fun `emitted initial state is correct`() {
        val state = states.onNextEvents().first()
        assertEquals(INITIAL_COUNTER, state.counter)
        assertEquals(INITIAL_LOADING, state.loading)
    }

    @Test
    fun `there should be no state emission besides the initial one for unfulfillable wishes`() {
        val actions = listOf(
            Unfulfillable,
            Unfulfillable,
            Unfulfillable
        )

        actions.forEach(this.actions::onNext)
        mainScheduler.triggerActions()

        assertEquals(1, states.onNextEvents().size)
    }

    @Test
    fun `there should be the same amount of states as actions that translate 1 - 1 to effects plus one for initial state`() {
        val actions = listOf(
            FulfillableInstantly,
            FulfillableInstantly,
            FulfillableInstantly
        )

        actions.forEach { action ->
            this.actions.onNext(action)
            reducerScheduler.triggerActions()
        }

        mainScheduler.triggerActions()


        assertEquals(1 + actions.size, states.onNextEvents().size)
    }

    @Test
    fun `there should be 3 times as many states as actions that translate 1 - 3 to effects plus one for initial state`() {
        val actions = listOf(
            TranslatesTo3Effects,
            TranslatesTo3Effects,
            TranslatesTo3Effects
        )

        actions.forEach { action ->
            this.actions.onNext(action)
            reducerScheduler.triggerActions()
        }
        mainScheduler.triggerActions()

        assertEquals(1 + actions.size * 3, states.onNextEvents().size)
    }

    @Test
    fun `the number of state emissions should reflect the number of effects plus one for initial state in complex case`() {
        val actions = listOf(
            FulfillableInstantly,  // maps to 1 effect
            FulfillableInstantly,  // maps to 1 effect
            MaybeFulfillable,       // maps to 1 in this case
            Unfulfillable,          // maps to 0
            FulfillableInstantly,  // maps to 1
            FulfillableInstantly,  // maps to 1
            MaybeFulfillable,       // maps to 0 in this case
            TranslatesTo3Effects    // maps to 3
        )

        actions.forEach { action ->
            this.actions.onNext(action)
            reducerScheduler.triggerActions()
        }
        mainScheduler.triggerActions()

        assertEquals(8 + 1, states.onNextEvents().size)
        val expectedState = TestState(
            counter = (INITIAL_COUNTER + 1 + 1) * 10 + 1 + 1 + 3 * 1,
            loading = false
        )
        assertEquals(expectedState, states.onNextEvents().last())
    }


    @Test
    fun `there should be no state emission after store destroying`() {
        val mockServerDelayMs = 10L

        actions.onNext(FulfillableAsync(mockServerDelayMs))

        reducerScheduler.triggerActions()
        mainScheduler.triggerActions()

        assertEquals(2, states.onNextEvents().size)

        val stateBeforeDestroy = states.onNextEvents().last()

        assertEquals(INITIAL_COUNTER, stateBeforeDestroy.counter)
        assertEquals(true, stateBeforeDestroy.loading)

        asyncWorkScheduler.advanceTimeBy(5, TimeUnit.MILLISECONDS)

        baseStore.destroy()

        asyncWorkScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS)

        mainScheduler.triggerActions()

        val stateAfterDestroy = states.onNextEvents().last()

        assertEquals(INITIAL_COUNTER, stateAfterDestroy.counter)
        assertEquals(true, stateAfterDestroy.loading)
    }

    @Test
    fun `last state should be delivered after view rebind`() {
        val mockServerDelayMs = 10L

        actions.onNext(FulfillableAsync(mockServerDelayMs))

        reducerScheduler.triggerActions()
        mainScheduler.triggerActions()

        assertEquals(2, states.onNextEvents().size)

        val stateBeforeUnbind = states.onNextEvents().last()

        assertEquals(INITIAL_COUNTER, stateBeforeUnbind.counter)
        assertEquals(true, stateBeforeUnbind.loading)

        asyncWorkScheduler.advanceTimeBy(5, TimeUnit.MILLISECONDS)

        baseStore.unbindView()

        asyncWorkScheduler.advanceTimeBy(10, TimeUnit.MILLISECONDS)

        mainScheduler.triggerActions()

        baseStore.bindView(view)

        reducerScheduler.triggerActions()
        mainScheduler.triggerActions()

        val stateAfterRebind = states.onNextEvents().last()

        assertEquals(INITIAL_COUNTER + DELAYED_FULFILL_AMOUNT, stateAfterRebind.counter)
        assertEquals(false, stateAfterRebind.loading)
    }
}