package ru.coderedwolf.mvi.store

import io.reactivex.Flowable
import ru.coderedwolf.mvi.core.elements.Bootstrapper
import ru.coderedwolf.mvi.core.elements.EventProducer
import ru.coderedwolf.mvi.core.elements.Middleware
import ru.coderedwolf.mvi.core.elements.Reducer

open class JustReducerStore<Action : Any, State : Any, Event : Any>(
    initialState: State,
    reducer: Reducer<State, Action>,
    bootstrapper: Bootstrapper<Action>? = null,
    eventProducer: EventProducer<State, Action, Event>? = null
) : OnlyActionStore<Action, State, Event>(
    initialState = initialState,
    reducer = reducer,
    middleware = BypassMiddleware<State, Action>(),
    bootstrapper = bootstrapper,
    eventProducer = eventProducer
) {

    class BypassMiddleware<State : Any, Action : Any> : Middleware<Action, State, Action> {

        override fun invoke(action: Action, state: State): Flowable<Action> = Flowable.just(action)
    }

}