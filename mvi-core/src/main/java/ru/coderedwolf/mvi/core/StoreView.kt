package ru.coderedwolf.mvi.core

import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

/**
 * @author HaronCode. Date 13.10.2019.
 */
interface StoreView<Action : Any, State : Any, Event : Any> : Consumer<State> {

    val actionSource: ObservableSource<Action>

    fun onEvent(event: Event)

}
