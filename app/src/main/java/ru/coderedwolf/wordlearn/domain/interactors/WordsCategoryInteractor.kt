package ru.coderedwolf.wordlearn.domain.interactors

import ru.coderedwolf.wordlearn.domain.reporitory.WordsCategoryRepository
import ru.coderedwolf.wordlearn.model.Category
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
interface WordsCategoryInteractor {

    suspend fun findAllCategory(): List<Category>

    suspend fun addCategory(category: Category): Category

    suspend fun remoteCategory(categoryId: Long)
}

class WordsCategoryInteractorImpl @Inject constructor(
        private val wordsCategoryRepository: WordsCategoryRepository
) : WordsCategoryInteractor {

    override suspend fun findAllCategory(): List<Category> = wordsCategoryRepository.findAll()

    override suspend fun addCategory(category: Category): Category = wordsCategoryRepository.save(category)

    override suspend fun remoteCategory(categoryId: Long) = wordsCategoryRepository.delete(categoryId)
}
