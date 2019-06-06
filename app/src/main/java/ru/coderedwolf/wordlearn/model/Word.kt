package ru.coderedwolf.wordlearn.model

import java.util.*

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
data class Word(
        val wordId: Long,
        val categoryId: Long,
        val word: String,
        val phraseToMemorize: String,
        val transcription: String,
        val translation: String,
        val lastReviewDate: Date,
        val examplesList: List<String>,
        val isRotate: Boolean,
        val reviewCount: Int
)