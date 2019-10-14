package ru.coderedwolf.mvi.core

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface Store<Action, State> {

    fun bindView(mviView: MviView<Action, State>)

    fun unbindView()
}