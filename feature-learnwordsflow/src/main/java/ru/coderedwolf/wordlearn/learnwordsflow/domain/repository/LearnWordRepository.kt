package ru.coderedwolf.wordlearn.learnwordsflow.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.database.dao.CategoryAndWordDao
import ru.coderedwolf.wordlearn.database.entity.CategoryAndWordEntity
import ru.coderedwolf.wordlearn.database.mapper.CategoryMapper
import ru.coderedwolf.wordlearn.database.mapper.WordMapper
import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
interface LearnWordRepository {
    suspend fun findMemberWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>>
    suspend fun findNewWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>>
}

class LearnWordRepositoryImpl @Inject constructor(
    private val categoryAndWordDao: CategoryAndWordDao,
    private val wordMapper: WordMapper,
    private val categoryMapper: CategoryMapper,
    private val dispatchersProvider: DispatchersProvider
) : LearnWordRepository {

    override suspend fun findMemberWordGroupByCategory(
        limit: Int
    ): Map<WordCategory, List<Word>> = withContext(dispatchersProvider.io()) {
        categoryAndWordDao.findAllMemberCategoryAndWordEntity(limit)
            .map { entity -> convertToPair(entity) }
            .toMap()
    }

    override suspend fun findNewWordGroupByCategory(
        limit: Int
    ): Map<WordCategory, List<Word>> = withContext(dispatchersProvider.io()) {
        categoryAndWordDao.findAllNewCategoryAndWordEntity(limit)
            .map { entity -> convertToPair(entity) }
            .toMap()
    }

    private fun convertToPair(entity: CategoryAndWordEntity): Pair<WordCategory, List<Word>> {
        val category = categoryMapper.convert(entity.categoryEntity)
        val list = entity.wordList
            .map { wordMapper.convert(it) }
            .sortedBy { it.reviewCount }
        return category to list
    }
}