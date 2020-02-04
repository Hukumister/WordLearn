package ru.haroncode.wordlearn.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author HaronCode.
 */
@Entity
data class PhraseTopicEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val title: String,
    val isStudy: Boolean
)