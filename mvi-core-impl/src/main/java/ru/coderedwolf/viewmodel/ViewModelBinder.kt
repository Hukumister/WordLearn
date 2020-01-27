package ru.coderedwolf.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import ru.coderedwolf.mvi.binder.ConnectionViewBinder
import ru.coderedwolf.mvi.binder.noneTransformer
import ru.coderedwolf.mvi.binder.with
import ru.coderedwolf.mvi.core.EventListener
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.mvi.core.StoreView
import ru.coderedwolf.mvi.elements.Navigator
import ru.coderedwolf.mvi.routing.EventListenerConnection
import ru.coderedwolf.mvi.routing.NavigationConnection

abstract class ViewModelBinder<Action : Any, State : Any, ViewState : Any, Event : Any>(
    store: Store<Action, State, Event>,
    navigator: Navigator<State, Event>,
    transformer: (State) -> ViewState,
    scheduler: Scheduler
) : ViewModel() {

    private val binderDelegate = ConnectionViewBinder<Action, ViewState, Event> { storeView ->
        add(NavigationConnection(store, scheduler, navigator))

        add(store to storeView with { input -> input.map(transformer).observeOn(scheduler) })
        add(storeView to store with noneTransformer())

        @Suppress("UNCHECKED_CAST")
        (storeView as? EventListener<Event>)
            ?.let { listener -> eventListenerConnection(store, scheduler, listener) }
            ?.let { connection -> add(connection) }

    }.apply { childConnectionBinder?.let(::addChild) }

    protected open val childConnectionBinder: ConnectionViewBinder<Action, ViewState, Event>? = null

    fun bindView(storeView: StoreView<Action, ViewState>) = binderDelegate.bind(storeView)

    fun unbindView() = binderDelegate.unbind()

    private fun <Event : Any> eventListenerConnection(
        store: Store<*, *, Event>,
        scheduler: Scheduler,
        eventListener: EventListener<Event>
    ) = EventListenerConnection(
        store = store,
        eventListener = eventListener,
        scheduler = scheduler
    )

    override fun onCleared() = binderDelegate.dispose()

}