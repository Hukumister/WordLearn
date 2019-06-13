package ru.coderedwolf.wordlearn.model.word

import java.util.*

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
        val lastReviewDate: Date? = null
)