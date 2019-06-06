package ru.coderedwolf.wordlearn.model.entity

import androidx.room.*
import ru.coderedwolf.wordlearn.domain.data.converter.StringListConverter
import java.util.*

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
@Entity(
        foreignKeys = [
            ForeignKey(
                    entity = CategoryEntity::class,
                    childColumns = ["categoryId"],
                    parentColumns = ["id"]
            )
        ],
        indices = [
            Index("categoryId")
        ]
)
@TypeConverters(value = [StringListConverter::class])
data class WordEntity(
        @PrimaryKey(autoGenerate = true) val wordId: Long? = null,
        val categoryId: Long,
        val word: String,
        val phraseToMemorize: String,
        val transcription: String,
        val translation: String,
        val reviewCount: Int,
        val lastReviewDate: Date,
        val examplesList: List<String>
)