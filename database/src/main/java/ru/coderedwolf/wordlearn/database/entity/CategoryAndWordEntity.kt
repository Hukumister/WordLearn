package ru.coderedwolf.wordlearn.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author HaronCode. Date 16.06.2019.
 */
data class CategoryAndWordEntity(
    @Embedded val categoryEntity: WordCategoryEntity,
    @Relation(entityColumn = "categoryId", parentColumn = "id") val wordList: List<WordEntity>
)