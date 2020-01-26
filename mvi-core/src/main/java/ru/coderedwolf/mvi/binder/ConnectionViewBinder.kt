package ru.coderedwolf.mvi.binder

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import ru.coderedwolf.mvi.core.StoreView

class ConnectionViewBinder<Action : Any, State : Any, Event : Any>(
    private val createConnection: MutableList<Connection<*, *>>.(StoreView<Action, State>) -> Unit
) : ViewBinder<Action, State, Event> {

    private val compositeDisposable = CompositeDisposable()
    private var childBinder: ConnectionViewBinder<Action, State, Event>? = null

    override fun bind(storeView: StoreView<Action, State>) {
        val connectionList = mutableListOf<Connection<*, *>>()
        connectionList.createConnection(storeView)
        connectionList.forEach { connection -> connect(connection) }

        childBinder?.bind(storeView)
    }

    fun addChild(binder: ConnectionViewBinder<Action, State, Event>) {
        childBinder = binder
    }

    private fun <In : Any, Out : Any> connect(
        connection: Connection<In, Out>
    ) = Flowable.fromPublisher(connection.publisher)
        .compose(connection.transformer)
        .subscribe(connection.consumer)
        .addTo(compositeDisposable)

    override fun unbind() {
        compositeDisposable.clear()
        childBinder?.unbind()
    }

    override fun isDisposed() = compositeDisposable.isDisposed

    override fun dispose() = compositeDisposable.dispose()

}