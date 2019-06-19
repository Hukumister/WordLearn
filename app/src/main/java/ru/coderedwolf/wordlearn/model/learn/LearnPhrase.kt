package ru.coderedwolf.wordlearn.model.learn

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
data class LearnPhrase(
        val phraseId: Long,
        val topicTitle: String,
        val phraseText: String,
        val phraseTranslate: String
)