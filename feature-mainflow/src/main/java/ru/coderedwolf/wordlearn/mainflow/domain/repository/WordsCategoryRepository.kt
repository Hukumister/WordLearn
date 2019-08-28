package ru.coderedwolf.wordlearn.mainflow.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.database.dao.WordsCategoryDao
import ru.coderedwolf.wordlearn.database.mapper.CategoryMapper
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
interface WordsCategoryRepository {
    suspend fun findAll(): List<WordCategory>
    suspend fun save(wordCategory: WordCategory): WordCategory
    suspend fun delete(categoryId: Long)
}

class WordsCategoryRepositoryImpl @Inject constructor(
    private val categoryDao: WordsCategoryDao,
    private val provider: DispatchersProvider,
    private val mapper: CategoryMapper
) : WordsCategoryRepository {

    override suspend fun findAll(): List<WordCategory> = withContext(provider.io()) {
        categoryDao.findAll().map { mapper.convert(it) }
    }

    override suspend fun save(wordCategory: WordCategory): WordCategory = withContext(provider.io()) {
        val entity = categoryDao.saveAndReturn(mapper.convertToEntity(wordCategory))
        mapper.convert(entity)
    }

    override suspend fun delete(categoryId: Long) = withContext(provider.io()) {
        categoryDao.remove(categoryId)
    }
}