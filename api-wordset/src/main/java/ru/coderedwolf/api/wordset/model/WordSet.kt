package ru.coderedwolf.api.wordset.model

/**
 * @author CodeRedWolf. Date 04.11.2019.
 */
data class WordSet(
    val id: Long,
    val title: String,
    val color: Int,
    val isUserSet: Boolean
)