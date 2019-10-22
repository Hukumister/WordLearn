package ru.coderedwolf.wordlearn.mvicore

import kotlinx.coroutines.flow.Flow

interface Bootstrapper<Action> {

    fun invoke(): Flow<Action>
}