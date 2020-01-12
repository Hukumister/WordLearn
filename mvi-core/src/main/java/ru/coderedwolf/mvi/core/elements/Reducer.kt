package ru.coderedwolf.mvi.core.elements

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
typealias Reducer<State, Effect> = (state: State, effect: Effect) -> State