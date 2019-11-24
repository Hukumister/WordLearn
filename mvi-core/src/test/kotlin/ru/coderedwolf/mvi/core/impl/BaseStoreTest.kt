package ru.coderedwolf.mvi.core.impl

import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * @author CodeRedWolf.
 */
class BaseStoreTest {

    private lateinit var baseStore: BaseStore<TestAction, TestViewState, TestViewEvent, TestEffect>
    private lateinit var states: TestObserver<TestViewState>
    private lateinit var actions: PublishSubject<TestAction>
    private lateinit var mainScheduler: TestScheduler
    private lateinit var singleScheduler: TestScheduler
    private lateinit var view: TestView

    @Before
    fun prepare() {

        mainScheduler = TestScheduler()
        singleScheduler = TestScheduler()

        baseStore = BaseStore(
            initialState = TestViewState(),
            mainScheduler = { mainScheduler },
            singleScheduler = { singleScheduler },
            reducer = TestReducer(),
            middleware = TestMiddleware(),
            bootstrapper = TestBootstrapper(),
            navigator = TestNavigator()
        )

        actions = PublishSubject.create()
        states = TestObserver()

        view = TestView(actions, states)
        baseStore.bindView(view)
    }

    @Test
    fun `if there are no actions, feature only emits initial state`() {
        mainScheduler.triggerActions()
        assertEquals(1, states.events[0].size)
    }

}