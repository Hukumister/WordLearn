package ru.haroncode.wordlearn.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

/**
 * @author HaronCode.
 */
@Entity
data class WordCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val isStudy: Boolean,
    val createDate: Instant
)
