package ru.coderedwolf.mvi.elements

/**
 * @author HaronCode.
 */
typealias Navigator<State, Event> = (state: State, event: Event) -> Unit