package ru.coderedwolf.wordlearn.mvicore

interface Navigator<State, Effect, NavigationEvent> {

    fun handle(state: State, effect: Effect): NavigationEvent?
}