package ru.coderedwolf.viewmodel

import ru.coderedwolf.mvi.core.elements.*

/**
 * @author CodeRedWolf. Date 08.11.2019.
 */
abstract class OnlyActionViewModelStore<Action, State, ViewEvent>(
    initialState: State,
    reducer: Reducer<State, Action>,
    middleware: Middleware<Action, State, Action>,
    viewEventProducer: ViewEventProducer<State, Action, ViewEvent>? = null,
    navigator: Navigator<State, Action>? = null,
    bootstrapper: Bootstrapper<Action>? = null
) : BaseViewModelStore<Action, State, ViewEvent, Action>(
    initialState = initialState,
    reducer = reducer,
    middleware = middleware,
    navigator = navigator,
    viewEventProducer = viewEventProducer,
    bootstrapper = bootstrapper
)