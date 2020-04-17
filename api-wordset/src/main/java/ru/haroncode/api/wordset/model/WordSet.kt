package ru.haroncode.api.wordset.model

/**
 * @author HaronCode.
 */
data class WordSet(
    val id: Long,
    val title: String,
    val color: Int,
    val isUserSet: Boolean
)
