package ru.coderedwolf.mvi.core.elements

/**
 * @author HaronCode.
 */
typealias EventProducer<State, Effect, Event> = (state: State, effect: Effect) -> Event?