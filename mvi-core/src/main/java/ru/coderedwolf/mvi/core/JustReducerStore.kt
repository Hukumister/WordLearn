package ru.coderedwolf.mvi.core

import io.reactivex.Flowable
import io.reactivex.Scheduler
import ru.coderedwolf.mvi.core.elements.EventProducer
import ru.coderedwolf.mvi.core.elements.Middleware
import ru.coderedwolf.mvi.core.elements.Reducer

class JustReducerStore<Action : Any, State : Any, Event : Any>(
    initialState: State,
    mainScheduler: Scheduler,
    reducerScheduler: Scheduler,
    reducer: Reducer<State, Action>,
    eventProducer: EventProducer<State, Action, Event>? = null
) : AbstractStore<Action, State, Event, Action>(
    initialState = initialState,
    reducerScheduler = reducerScheduler,
    mainScheduler = mainScheduler,
    reducer = reducer,
    middleware = BypassMiddleware<State, Action>(),
    eventProducer = eventProducer
) {

    class BypassMiddleware<State : Any, Action : Any> : Middleware<Action, State, Action> {

        override fun invoke(action: Action, state: State): Flowable<Action> =
            Flowable.just(action)
    }
}