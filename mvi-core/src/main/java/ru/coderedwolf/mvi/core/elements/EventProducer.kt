package ru.coderedwolf.mvi.core.elements

/**
 * @author CodeRedWolf. Date 05.11.2019.
 */
typealias EventProducer<State, Effect, Event> = (state: State, effect: Effect) -> Event?