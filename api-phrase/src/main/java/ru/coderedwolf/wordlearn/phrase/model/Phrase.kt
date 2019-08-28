package ru.coderedwolf.wordlearn.phrase.model

import org.threeten.bp.Instant

/**
 * @author CodeRedWolf. Date 15.06.2019.
 */
data class Phrase(
    val id: Long? = null,
    val topicId: Long,
    val textPhrase: String,
    val translation: String,
    val reviewCount: Int,
    val lastReviewDate: Instant?
)