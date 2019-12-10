package ru.coderedwolf.mvi.core.impl

/**
 * @author CodeRedWolf.
 */

const val INITIAL_COUNTER = 10

data class TestViewState(
    val data: List<String> = emptyList()
)