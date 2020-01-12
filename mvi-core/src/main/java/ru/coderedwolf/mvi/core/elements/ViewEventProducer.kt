package ru.coderedwolf.mvi.core.elements

/**
 * @author CodeRedWolf. Date 05.11.2019.
 */
typealias ViewEventProducer<State, Effect, ViewEvent> = (state: State, effect: Effect) -> ViewEvent?