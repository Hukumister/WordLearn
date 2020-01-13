package ru.coderedwolf.mvi.core

interface LifecycleStore<Action, State, Even> : Store<Action, State, Even> {

    /**
     *
     */
    fun create()

    /**
     *
     */
    fun destroy()
}