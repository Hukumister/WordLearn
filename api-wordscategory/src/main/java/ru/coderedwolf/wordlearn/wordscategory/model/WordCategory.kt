package ru.coderedwolf.wordlearn.wordscategory.model

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
data class WordCategory(
    val id: Long? = null,
    val name: String,
    val isStudy: Boolean,
    val progress: Int = 0
)