package ru.coderedwolf.wordlearn.mvicore

import kotlinx.coroutines.flow.Flow

interface Middleware<Action, State, Effect> {

    fun handle(action: Action, state: State): Flow<Effect>
}