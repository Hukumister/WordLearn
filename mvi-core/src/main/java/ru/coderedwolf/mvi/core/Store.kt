package ru.coderedwolf.mvi.core

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface Store<Action, State, Event> {

    /**
     * This method must be invoked for bind view to store.
     */
    fun bindView(mviView: MviView<Action, State, Event>)

    /**
     *
     */
    fun unbindView()
}