package ru.coderedwolf.mvi.core

import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider

/**
 * @author CodeRedWolf. Date 08.11.2019.
 */
abstract class OnlyActionViewModelStore<Action, State, ViewEvent>(
    initialState: State,
    reducer: Reducer<State, Action>,
    schedulerProvider: SchedulerProvider,
    middleware: Middleware<Action, State, Action>,
    viewEventProducer: ViewEventProducer<State, Action, ViewEvent>? = null,
    navigator: Navigator<State, Action>? = null,
    bootstrapper: Bootstrapper<Action>? = null
) : BaseViewModelStore<Action, State, Action, ViewEvent>(
    initialState = initialState,
    reducer = reducer,
    schedulerProvider = schedulerProvider,
    middleware = middleware,
    navigator = navigator,
    viewEventProducer = viewEventProducer,
    bootstrapper = bootstrapper
)