package ru.coderedwolf.mvi.store

import ru.coderedwolf.mvi.core.AbstractStore
import ru.coderedwolf.mvi.elements.Bootstrapper
import ru.coderedwolf.mvi.elements.EventProducer
import ru.coderedwolf.mvi.elements.Middleware
import ru.coderedwolf.mvi.elements.Reducer

open class BaseStore<Action : Any, State : Any, Event : Any, Effect : Any>(
    initialState: State,
    reducer: Reducer<State, Effect>,
    middleware: Middleware<Action, State, Effect>,
    bootstrapper: Bootstrapper<Action>? = null,
    eventProducer: EventProducer<State, Effect, Event>? = null
) : AbstractStore<Action, State, Event, Effect>(
    initialState = initialState,
    reducer = reducer,
    middleware = middleware,
    bootstrapper = bootstrapper,
    eventProducer = eventProducer
)