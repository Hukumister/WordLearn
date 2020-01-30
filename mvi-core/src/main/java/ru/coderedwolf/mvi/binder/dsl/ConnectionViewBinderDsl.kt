package ru.coderedwolf.mvi.binder.dsl

import ru.coderedwolf.mvi.binder.ConnectionViewBinder
import ru.coderedwolf.mvi.connection.ConnectionRule
import ru.coderedwolf.mvi.core.StoreView

fun <Action : Any, State : Any> binding(
    listener: MutableList<ConnectionRule>.(storeView: StoreView<Action, State>) -> Unit
): ConnectionViewBinder<Action, State> {
    val connectionViewBindingBuilder = mutableListOf<ConnectionRule>()

    return ConnectionViewBinder { storeView ->
        connectionViewBindingBuilder.apply { listener(storeView) }
    }
}

inline fun MutableList<ConnectionRule>.connection(rule: () -> ConnectionRule) {
    rule.invoke().let(::add)
}