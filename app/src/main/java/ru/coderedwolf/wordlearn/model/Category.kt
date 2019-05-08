package ru.coderedwolf.wordlearn.model

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
data class Category(
        val id: Long? = null,
        val name: String,
        val image: String,
        val progress: Int
)