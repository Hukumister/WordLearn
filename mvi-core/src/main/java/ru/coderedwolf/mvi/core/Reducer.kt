package ru.coderedwolf.mvi.core

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface Reducer<State, Effect> {

    fun reduce(state: State, effect: Effect): State
}
