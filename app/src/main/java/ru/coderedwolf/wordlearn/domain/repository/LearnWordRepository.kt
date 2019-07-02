package ru.coderedwolf.wordlearn.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.CategoryMapper
import ru.coderedwolf.wordlearn.domain.mappers.WordMapper
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.model.entity.CategoryAndWordEntity
import ru.coderedwolf.wordlearn.model.word.Word
import ru.coderedwolf.wordlearn.model.word.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
interface LearnWordRepository {

    suspend fun findMemberWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>>
    suspend fun findNewWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>>
}

class LearnWordRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val wordMapper: WordMapper,
        private val categoryMapper: CategoryMapper,
        private val dispatchersProvider: DispatchersProvider
) : LearnWordRepository {

    private val categoryAndWordDao = dataBase.categoryAndWordDao()

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