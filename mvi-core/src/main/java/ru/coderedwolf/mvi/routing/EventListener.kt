package ru.coderedwolf.mvi.routing

interface EventListener<Event : Any> {

    fun onEvent(event: Event)

}