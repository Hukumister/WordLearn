package ru.coderedwolf.mvi.core

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
typealias Navigator<State, Effect, NavigationEvent> = (state: State, effect: Effect) -> NavigationEvent?