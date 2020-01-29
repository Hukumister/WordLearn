package ru.coderedwolf.mvi.core.elements

/**
 * @author HaronCode.
 */
typealias Navigator<State, Event> = (state: State, event: Event) -> Unit