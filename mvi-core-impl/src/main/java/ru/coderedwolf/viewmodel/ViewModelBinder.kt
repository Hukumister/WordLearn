package ru.coderedwolf.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.coderedwolf.mvi.binder.ConnectionViewBinder
import ru.coderedwolf.mvi.binder.ViewBinder
import ru.coderedwolf.mvi.binder.dsl.*
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
    uiScheduler: Scheduler = AndroidSchedulers.mainThread()
) : ViewModel(), ViewBinder<Action, ViewState> {

    private val binderDelegate = binding<Action, ViewState> { storeView ->

        connection { store to storeView with { input -> input.map(transformer).observeOn(uiScheduler) } }
        connection { storeView to store with noneTransformer() }

        val navigationConnection = NavigationConnection(store, store.eventSource, navigator)
        connection { navigationConnection decorate { stream -> stream.observeOn(uiScheduler) } }

        (storeView as? EventListener<Event>)
            ?.let { listener -> eventListenerConnection(store, listener) }
            ?.let { eventListenerConnection ->

                connection {
                    eventListenerConnection decorate { stream -> stream.observeOn(uiScheduler) }
                }
            }
    }

    protected open val childBinding: ConnectionViewBinder<Action, ViewState>? = null

    override fun bindView(storeView: StoreView<Action, ViewState>) = binderDelegate.bindView(storeView)

    override fun unbindView() = binderDelegate.unbindView()

    private fun <Event : Any> eventListenerConnection(
        store: Store<*, *, Event>,
        eventListener: EventListener<Event>
    ) = EventListenerConnection(
        eventPublisher = store.eventSource,
        eventListener = eventListener
    )

    override fun onCleared() = store.dispose()

}