package ru.haroncode.wordlearn.word.model

/**
 * @author HaronCode. Date 06.06.2019.
 */
data class WordPreview(
    val wordId: Long,
    val word: String,
    val reviewCount: Int,
    val translation: String
)
