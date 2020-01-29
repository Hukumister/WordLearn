package ru.coderedwolf.mvi.core.elements

/**
 * @author HaronCode.
 */
typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State