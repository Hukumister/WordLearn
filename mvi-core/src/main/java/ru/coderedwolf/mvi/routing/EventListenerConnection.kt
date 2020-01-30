package ru.coderedwolf.mvi.routing

import io.reactivex.functions.Consumer
import org.reactivestreams.Publisher
import ru.coderedwolf.mvi.connection.BaseConnectionRule

class EventListenerConnection<Event : Any>(
    eventPublisher: Publisher<Event>,
    eventListener: EventListener<Event>
) : BaseConnectionRule<Event, Event>(
    publisher = eventPublisher,
    consumer = Consumer { event -> eventListener.onEvent(event) },
    transformer = { input -> input }
)