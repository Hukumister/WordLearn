package ru.coderedwolf.mvi.core

interface EventListener<Event : Any> {

    fun onEvent(event: Event)

}