package ru.coderedwolf.mvi.core

/**
 * @author CodeRedWolf. Date 13.10.2019.
 */
typealias Navigator<State, Effect> = (state: State, effect: Effect) -> Unit