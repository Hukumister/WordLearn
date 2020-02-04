package ru.coderedwolf.wordlearn.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author HaronCode. Date 04.11.2019.
 */
@Entity
data class WordSetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val title: String,
    val color: Int,
    val isUserSet: Boolean
)