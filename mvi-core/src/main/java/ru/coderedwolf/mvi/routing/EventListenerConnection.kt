package ru.coderedwolf.mvi.routing

import io.reactivex.Scheduler
import io.reactivex.functions.Consumer
import ru.coderedwolf.mvi.binder.Connection
import ru.coderedwolf.mvi.core.EventListener
import ru.coderedwolf.mvi.core.Store

class EventListenerConnection<Event : Any>(
    store: Store<*, *, Event>,
    eventListener: EventListener<Event>,
    scheduler: Scheduler
) : Connection<Event, Event>(
    publisher = store.eventSource,
    consumer = Consumer { event -> eventListener.onEvent(event) },
    transformer = { input -> input.observeOn(scheduler) }
)