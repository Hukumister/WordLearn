package ru.coderedwolf.wordlearn.wordscategory.domain

import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory

interface WordsCategoryInteractor {
    suspend fun findAllCategory(): List<WordCategory>
    suspend fun addCategory(wordCategory: WordCategory): WordCategory
    suspend fun remoteCategory(categoryId: Long)
    suspend fun changeLearnStatus(categoryId: Long, isStudy: Boolean)
}