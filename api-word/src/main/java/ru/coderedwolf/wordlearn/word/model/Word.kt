package ru.coderedwolf.wordlearn.word.model

import org.threeten.bp.Instant

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
data class Word(
    val wordId: Long? = null,
    val categoryId: Long,
    val word: String,
    val association: String,
    val transcription: String,
    val translation: String,
    val examplesList: List<WordExample>,
    val reviewCount: Int = 0,
    val isStudy: Boolean = true,
    val lastReviewDate: Instant? = null
)