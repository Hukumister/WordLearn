package ru.coderedwolf.wordlearn.mvicore

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import ru.coderedwolf.wordlearn.common.DispatchersProvider

@ExperimentalCoroutinesApi
@FlowPreview
abstract class ReducerViewModelStore<Action, State, NavigationEvent>(
    dispatchersProvider: DispatchersProvider,
    initialState: State,
    reducer: Reducer<State, Action>,
    navigator: Navigator<State, Action, NavigationEvent>? = null,
    bootstrapper: Bootstrapper<Action>? = null
) : BaseViewModelStore<Action, State, Action, NavigationEvent>(
    dispatchersProvider = dispatchersProvider,
    initialState = initialState,
    reducer = reducer,
    middleware = BypassMiddleware<State, Action>(),
    navigator = navigator,
    bootstrapper = bootstrapper
) {

    class BypassMiddleware<State, Action> : Middleware<Action, State, Action> {

        override fun handle(action: Action, state: State): Flow<Action> = flowOf(action)
    }
}