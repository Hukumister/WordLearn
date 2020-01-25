package ru.coderedwolf.mvi.elements

/**
 * @author HaronCode.
 */
typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State