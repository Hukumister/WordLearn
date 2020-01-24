package ru.coderedwolf.mvi.core

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.reactivestreams.Publisher

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface Store<Action : Any, State : Any, Event : Any> : Consumer<Action>, Disposable {

    val stateSource: Publisher<State>

    val eventSource: Publisher<Event>

    interface Factory {

        fun <Action : Any, State : Any, Event : Any> create(
            initialState: State
        ): Store<Action, State, Event>

    }

}