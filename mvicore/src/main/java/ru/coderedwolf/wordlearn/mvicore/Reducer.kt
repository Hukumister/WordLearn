package ru.coderedwolf.wordlearn.mvicore

interface Reducer<State, Effect> {

    fun reduce(state: State, effect: Effect): State
}