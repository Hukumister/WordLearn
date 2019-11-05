package ru.coderedwolf.mvi.core

import io.reactivex.Observable

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface MviView<Action, State, Event> {

    val actions: Observable<Action>
    fun render(state: State)
    fun route(event: Event)
}
