package ru.coderedwolf.wordlearn.model.entity

import androidx.room.*
import ru.coderedwolf.wordlearn.domain.data.converter.WordExampleConverter
import ru.coderedwolf.wordlearn.model.WordExample
import java.util.*

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
@Entity(
        foreignKeys = [
            ForeignKey(
                    entity = WordCategoryEntity::class,
                    childColumns = ["categoryId"],
                    parentColumns = ["id"]
            )
        ],
        indices = [
            Index("categoryId")
        ]
)
@TypeConverters(value = [WordExampleConverter::class])
data class WordEntity(
        @PrimaryKey(autoGenerate = true) val wordId: Long? = null,
        val categoryId: Long,
        val word: String,
        val phraseToMemorize: String,
        val transcription: String,
        val translation: String,
        val reviewCount: Int,
        val lastReviewDate: Date?,
        val examplesList: List<WordExample>
)