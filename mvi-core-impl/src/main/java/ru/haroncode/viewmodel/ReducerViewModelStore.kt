package ru.haroncode.viewmodel

import io.reactivex.Flowable
import ru.haroncode.mvi.core.elements.*

/**
 * @author HaronCode. Date 14.10.2019.
 */
abstract class ReducerViewModelStore<Action, State, ViewEvent>(
    initialState: State,
    reducer: Reducer<State, Action>,
    eventProducer: EventProducer<State, Action, ViewEvent>? = null,
    navigator: Navigator<State, Action>? = null,
    bootstrapper: Bootstrapper<Action>? = null
) : OnlyActionViewModelStore<Action, State, ViewEvent>(
    initialState = initialState,
    reducer = reducer,
    middleware = BypassMiddleware<State, Action>(),
    navigator = navigator,
    eventProducer = eventProducer,
    bootstrapper = bootstrapper
) {

    class BypassMiddleware<State, Action> : Middleware<Action, State, Action> {

        override fun invoke(action: Action, state: State): Flowable<Action> =
            Flowable.just(action)
    }
}