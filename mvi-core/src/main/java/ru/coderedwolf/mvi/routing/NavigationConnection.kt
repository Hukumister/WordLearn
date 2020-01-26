package ru.coderedwolf.mvi.routing

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.Flowables
import ru.coderedwolf.mvi.binder.Connection
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.mvi.elements.Navigator

class NavigationConnection<State : Any, Event : Any>(
    store: Store<*, State, Event>,
    private val scheduler: Scheduler,
    private val navigator: Navigator<State, Event>
) : Connection<Pair<State, Event>, Pair<State, Event>>(
    publisher = Flowables.combineLatest(
        Flowable.fromPublisher(store),
        Flowable.fromPublisher(store.eventSource)
    ),
    consumer = Consumer { (state, event) -> navigator.invoke(state, event) },
    transformer = { input -> input.observeOn(scheduler) }
)
