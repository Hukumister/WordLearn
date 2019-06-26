package ru.coderedwolf.wordlearn.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
@Entity(
        foreignKeys = [
            ForeignKey(
                    entity = PhraseTopicEntity::class,
                    childColumns = ["topicId"],
                    parentColumns = ["id"]
            )
        ],
        indices = [
            Index("topicId")
        ]
)
data class PhraseEntity(
        @PrimaryKey(autoGenerate = true) val id: Long? = null,
        val topicId: Long,
        val textPhrase: String,
        val translation: String,
        val reviewCount: Int,
        val lastReviewDate: Instant?
)