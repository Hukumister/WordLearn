package ru.haroncode.wordlearn.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author HaronCode.
 */
data class TopicAndPhrasesEntity(
    @Embedded val phraseTopic: PhraseTopicEntity,
    @Relation(entityColumn = "topicId", parentColumn = "id") val phrases: List<PhraseEntity>
)
