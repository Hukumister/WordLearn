package ru.coderedwolf.wordlearn.wordflow.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.database.dao.WordDao
import ru.coderedwolf.wordlearn.database.mapper.WordMapper
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.word.model.WordPreview
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val wordMapper: WordMapper,
    private val dispatchersProvider: DispatchersProvider
) : WordRepository {

    override suspend fun findAllPreviewByCategoryId(
        categoryId: Long
    ): List<WordPreview> = withContext(dispatchersProvider.io()) {
        wordDao.findAllByCategory(categoryId).map { wordMapper.convertToPreview(it) }
    }

    override suspend fun findAllByCategoryId(
        categoryId: Long
    ): List<Word> = withContext(dispatchersProvider.io()) {
        wordDao.findAllByCategory(categoryId).map { wordMapper.convert(it) }
    }

    override suspend fun save(word: Word): Word = withContext(dispatchersProvider.io()) {
        val wordEntity = wordDao.saveAndReturn(wordMapper.convertToEntity(word))
        wordMapper.convert(wordEntity)
    }

    override suspend fun saveAll(list: List<Word>) = withContext(dispatchersProvider.io()) {
        wordDao.saveAll(list.map { wordMapper.convertToEntity(it) })
    }

    override suspend fun setIsStudy(wordId: Long, isStudy: Boolean) = withContext(dispatchersProvider.io()) {
        wordDao.setIsStudy(wordId, isStudy)
    }

    override suspend fun incReviewCount(wordId: Long) = withContext(dispatchersProvider.io()) {
        wordDao.incReview(wordId)
    }

    override suspend fun count(): Int = withContext(dispatchersProvider.io()) {
        wordDao.count()
    }
}