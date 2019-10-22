package ru.coderedwolf.wordlearn.mvicore

import kotlinx.coroutines.flow.Flow

interface MviView<Action, State> {

    val actions: Flow<Action>
    fun render(state: State)
}