package ru.coderedwolf.wordlearn.mainflow.domain.interactor

import ru.coderedwolf.wordlearn.mainflow.domain.repository.WordsCategoryRepository
import ru.coderedwolf.wordlearn.wordscategory.domain.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
class WordsCategoryInteractorImpl @Inject constructor(
    private val wordsCategoryRepository: WordsCategoryRepository
) : WordsCategoryInteractor {

    override suspend fun findAllCategory(): List<WordCategory> = wordsCategoryRepository.findAll()

    override suspend fun addCategory(wordCategory: WordCategory): WordCategory = wordsCategoryRepository.save(wordCategory)

    override suspend fun remoteCategory(categoryId: Long) = wordsCategoryRepository.delete(categoryId)

    override suspend fun changeLearnStatus(categoryId: Long, isStudy: Boolean) {

    }
}
