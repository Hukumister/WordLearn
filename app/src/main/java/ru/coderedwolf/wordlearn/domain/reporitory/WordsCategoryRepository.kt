package ru.coderedwolf.wordlearn.domain.reporitory

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.CategoryMapper
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.model.Category
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
interface WordsCategoryRepository {

    suspend fun findAll(): List<Category>

    suspend fun save(category: Category): Category

    suspend fun delete(categoryId: Long)
}

class WordsCategoryRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val provider: DispatchersProvider,
        private val mapper: CategoryMapper
) : WordsCategoryRepository {

    private val categoryDao = dataBase.wordsCategoryDao()

    override suspend fun findAll(): List<Category> = withContext(provider.io()) {
        categoryDao.findAll().map { mapper.convert(it) }
    }

    override suspend fun save(category: Category): Category = withContext(provider.io()) {
        val entity = categoryDao.saveAndReturn(mapper.convertToEntity(category))
        mapper.convert(entity)
    }

    override suspend fun delete(categoryId: Long) = withContext(provider.io()) {
        categoryDao.remove(categoryId)
    }
}