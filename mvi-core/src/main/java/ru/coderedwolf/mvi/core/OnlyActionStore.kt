package ru.coderedwolf.mvi.core

import ru.coderedwolf.mvi.elements.Bootstrapper
import ru.coderedwolf.mvi.elements.EventProducer
import ru.coderedwolf.mvi.elements.Middleware
import ru.coderedwolf.mvi.elements.Reducer

open class OnlyActionStore<Action : Any, State : Any, Event : Any>(
    initialState: State,
    reducer: Reducer<State, Action>,
    middleware: Middleware<Action, State, Action>,
    bootstrapper: Bootstrapper<Action>? = null,
    eventProducer: EventProducer<State, Action, Event>? = null
) : BaseStore<Action, State, Event, Action>(
    initialState = initialState,
    reducer = reducer,
    middleware = middleware,
    bootstrapper = bootstrapper,
    eventProducer = eventProducer
)