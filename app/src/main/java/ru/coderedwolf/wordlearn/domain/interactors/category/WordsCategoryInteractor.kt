package ru.coderedwolf.wordlearn.domain.interactors.category

import ru.coderedwolf.wordlearn.domain.reporitory.WordsCategoryRepository
import ru.coderedwolf.wordlearn.model.word.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
interface WordsCategoryInteractor {

    suspend fun findAllCategory(): List<WordCategory>

    suspend fun addCategory(wordCategory: WordCategory): WordCategory

    suspend fun remoteCategory(categoryId: Long)
}

class WordsCategoryInteractorImpl @Inject constructor(
        private val wordsCategoryRepository: WordsCategoryRepository
) : WordsCategoryInteractor {

    override suspend fun findAllCategory(): List<WordCategory> = wordsCategoryRepository.findAll()

    override suspend fun addCategory(wordCategory: WordCategory): WordCategory = wordsCategoryRepository.save(wordCategory)

    override suspend fun remoteCategory(categoryId: Long) = wordsCategoryRepository.delete(categoryId)
}
