package ru.coderedwolf.mvi.routing

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.Flowables
import org.reactivestreams.Publisher
import ru.coderedwolf.mvi.connection.BaseConnectionRule
import ru.coderedwolf.mvi.core.elements.Navigator

class NavigationConnection<State : Any, Event : Any>(
    statePublisher: Publisher<State>,
    eventPublisher: Publisher<Event>,
    private val scheduler: Scheduler,
    private val navigator: Navigator<State, Event>
) : BaseConnectionRule<Pair<State, Event>, Pair<State, Event>>(
    publisher = Flowables.combineLatest(
        Flowable.fromPublisher(statePublisher),
        Flowable.fromPublisher(eventPublisher)
    ),
    consumer = Consumer { (state, event) -> navigator.invoke(state, event) },
    transformer = { input -> input.observeOn(scheduler) }
)
