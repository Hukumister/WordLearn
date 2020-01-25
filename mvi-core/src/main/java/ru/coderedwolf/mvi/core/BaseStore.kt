package ru.coderedwolf.mvi.core

import io.reactivex.Flowable
import io.reactivex.rxkotlin.Flowables
import org.reactivestreams.Publisher
import ru.coderedwolf.mvi.core.elements.EventProducer
import ru.coderedwolf.mvi.core.elements.Middleware
import ru.coderedwolf.mvi.core.elements.Reducer

open class BaseStore<Action : Any, State : Any, Event : Any, Effect : Any>(
    initialState: State,
    reducer: Reducer<State, Effect>,
    middleware: Middleware<Action, State, Effect>,
    eventProducer: EventProducer<State, Effect, Event>? = null
) : AbstractStore<Action, State, Event, Effect>(
    initialState = initialState,
    reducer = reducer,
    middleware = middleware,
    eventProducer = eventProducer
), StateEffectProducer<State, Effect> {

    override val stateEffectSource: Publisher<Pair<State, Effect>>
        get() {
            val stateFlowable = Flowable.fromPublisher(stateSource)
                .skip(1)

            return Flowables.zip(stateFlowable, effectSource)
        }


}