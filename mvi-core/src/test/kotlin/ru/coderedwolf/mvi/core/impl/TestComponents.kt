package ru.coderedwolf.mvi.core.impl

import io.reactivex.Observable
import ru.coderedwolf.mvi.core.elements.Bootstrapper
import ru.coderedwolf.mvi.core.elements.Middleware
import ru.coderedwolf.mvi.core.elements.Navigator
import ru.coderedwolf.mvi.core.elements.Reducer

/**
 * @author CodeRedWolf.
 */
class TestReducer : Reducer<TestViewState, TestEffect> {

    override fun invoke(state: TestViewState, effect: TestEffect): TestViewState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class TestMiddleware : Middleware<TestAction, TestViewState, TestEffect> {

    override fun invoke(action: TestAction, state: TestViewState): Observable<TestEffect> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class TestBootstrapper : Bootstrapper<TestAction> {

    override fun invoke(): Observable<TestAction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

class TestNavigator : Navigator<TestViewState, TestEffect> {

    override fun invoke(state: TestViewState, effect: TestEffect) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}