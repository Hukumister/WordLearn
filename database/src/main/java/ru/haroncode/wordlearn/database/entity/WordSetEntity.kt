package ru.haroncode.wordlearn.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author HaronCode.
 */
@Entity
data class WordSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val title: String,
    val color: Int,
    val isUserSet: Boolean
)
