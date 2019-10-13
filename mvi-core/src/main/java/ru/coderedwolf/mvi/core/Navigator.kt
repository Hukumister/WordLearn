package ru.coderedwolf.mvi.core

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
interface Navigator<State, Effect, Event> {

    fun handle(state: State, effect: Effect): Event?
}