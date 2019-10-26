package ru.coderedwolf.mvi.core

import io.reactivex.Observable

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface MviView<Action, State> {

    val actions: Observable<Action>
    fun render(state: State)
}
