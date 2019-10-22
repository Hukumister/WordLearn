package ru.coderedwolf.wordlearn.mainflow.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.coderedwolf.wordlearn.database.dao.WordsCategoryDao
import ru.coderedwolf.wordlearn.database.mapper.CategoryMapper
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
interface WordsCategoryRepository {
    fun findAll(): Single<List<WordCategory>>
    fun save(wordCategory: WordCategory): Completable
    fun delete(wordCategory: WordCategory): Completable
}

class WordsCategoryRepositoryImpl @Inject constructor(
        private val categoryDao: WordsCategoryDao,
        private val mapper: CategoryMapper
) : WordsCategoryRepository {

    override fun findAll() = categoryDao.findAll()
            .map(mapper::convertList)

    override fun save(wordCategory: WordCategory) = wordCategory
            .let(mapper::convertToEntity)
            .let(categoryDao::save)

    override fun delete(wordCategory: WordCategory) = wordCategory
            .let(mapper::convertToEntity)
            .let(categoryDao::remove)
}