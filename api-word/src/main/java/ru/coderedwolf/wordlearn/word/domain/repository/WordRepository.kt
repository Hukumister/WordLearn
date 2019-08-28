package ru.coderedwolf.wordlearn.word.domain.repository

import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.word.model.WordPreview

interface WordRepository {
    suspend fun findAllPreviewByCategoryId(categoryId: Long): List<WordPreview>
    suspend fun findAllByCategoryId(categoryId: Long): List<Word>
    suspend fun save(word: Word): Word
    suspend fun saveAll(list: List<Word>)
    suspend fun setIsStudy(wordId: Long, isStudy: Boolean)
    suspend fun incReviewCount(wordId: Long)
    suspend fun count(): Int
}