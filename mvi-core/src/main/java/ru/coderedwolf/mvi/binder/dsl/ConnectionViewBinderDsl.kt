package ru.coderedwolf.mvi.binder.dsl

import ru.coderedwolf.mvi.binder.ConnectionViewBinder
import ru.coderedwolf.mvi.connection.ConnectionRule
import ru.coderedwolf.mvi.core.StoreView

fun <Action : Any, State : Any> binding(
    listener: ConnectionViewBindingBuilder.(storeView: StoreView<Action, State>) -> Unit
): ConnectionViewBinder<Action, State> {
    val connectionViewBindingBuilder = ConnectionViewBindingBuilder()

    return ConnectionViewBinder { storeView ->
        connectionViewBindingBuilder.listener(storeView)
        connectionViewBindingBuilder.createConnectionList()
    }
}


class ConnectionViewBindingBuilder {

    private val connectionRuleList = mutableListOf<ConnectionRule>()

    fun connection(rule: () -> ConnectionRule?) {
        rule.invoke()?.let(connectionRuleList::add)
    }

    fun createConnectionList() = connectionRuleList.toList()

}