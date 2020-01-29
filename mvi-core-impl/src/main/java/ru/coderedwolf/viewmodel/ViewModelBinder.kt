package ru.coderedwolf.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import ru.coderedwolf.mvi.binder.ConnectionViewBinder
import ru.coderedwolf.mvi.binder.ViewBinder
import ru.coderedwolf.mvi.binder.dsl.binding
import ru.coderedwolf.mvi.binder.dsl.noneTransformer
import ru.coderedwolf.mvi.binder.dsl.with
import ru.coderedwolf.mvi.core.Store
import ru.coderedwolf.mvi.core.StoreView
import ru.coderedwolf.mvi.core.elements.Navigator
import ru.coderedwolf.mvi.routing.EventListener
import ru.coderedwolf.mvi.routing.EventListenerConnection
import ru.coderedwolf.mvi.routing.NavigationConnection

abstract class ViewModelBinder<Action : Any, State : Any, ViewState : Any, Event : Any>(
    private val store: Store<Action, State, Event>,
    navigator: Navigator<State, Event>,
    transformer: (State) -> ViewState,
    scheduler: Scheduler
) : ViewModel(), ViewBinder<Action, ViewState> {

    private val binderDelegate = binding<Action, ViewState> { storeView ->

        connection { store to storeView with { input -> input.map(transformer).observeOn(scheduler) } }
        connection { storeView to store with noneTransformer() }
        connection { NavigationConnection(store, store.eventSource, scheduler, navigator) }

        connection {
            (storeView as? EventListener<Event>)
                ?.let { listener -> eventListenerConnection(store, scheduler, listener) }

        }

    }

    protected open val childBinding: ConnectionViewBinder<Action, ViewState>? = null

    override fun bindView(storeView: StoreView<Action, ViewState>) = binderDelegate.bindView(storeView)

    override fun unbindView() = binderDelegate.unbindView()

    private fun <Event : Any> eventListenerConnection(
        store: Store<*, *, Event>,
        scheduler: Scheduler,
        eventListener: EventListener<Event>
    ) = EventListenerConnection(
        eventPublisher = store.eventSource,
        eventListener = eventListener,
        scheduler = scheduler
    )

    override fun onCleared() = store.dispose()

}