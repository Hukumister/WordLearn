package ru.coderedwolf.mvi.binder

import io.reactivex.disposables.Disposable
import ru.coderedwolf.mvi.core.StoreView

interface ViewBinder<Action : Any, State : Any, Event : Any> : Disposable {

    fun bind(storeView: StoreView<Action, State>)

    fun unbind()
}
