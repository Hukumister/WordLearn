package ru.coderedwolf.wordlearn.model.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
data class TopicAndPhrasesEntity(
        @Embedded val phraseTopic: PhraseTopicEntity,
        @Relation(entityColumn = "topicId", parentColumn = "id") val phrases: List<PhraseEntity>
)