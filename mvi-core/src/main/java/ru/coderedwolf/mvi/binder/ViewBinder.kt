package ru.coderedwolf.mvi.binder

import ru.coderedwolf.mvi.core.StoreView

interface ViewBinder<Action : Any, State : Any> {

    fun bindView(storeView: StoreView<Action, State>)

    fun unbindView()
}
