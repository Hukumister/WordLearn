package ru.coderedwolf.mvi.elements

/**
 * @author HaronCode.
 */
typealias EventProducer<State, Effect, Event> = (state: State, effect: Effect) -> Event?