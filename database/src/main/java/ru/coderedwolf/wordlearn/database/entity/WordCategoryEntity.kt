package ru.coderedwolf.wordlearn.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.Instant

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
@Entity
data class WordCategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val name: String,
    val isStudy: Boolean,
    val createDate: Instant
)