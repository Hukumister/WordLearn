package ru.coderedwolf.wordlearn.domain.reporitory

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.WordMapper
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.model.Word
import ru.coderedwolf.wordlearn.model.WordPreview
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
interface WordRepository {

    suspend fun findAllPreviewByCategoryId(categoryId: Long): List<WordPreview>

    suspend fun findAllByCategoryId(categoryId: Long): List<Word>

    suspend fun save(word: Word): Word
}

class WordRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val wordMapper: WordMapper,
        private val dispatchersProvider: DispatchersProvider
) : WordRepository {

    private val wordDao = dataBase.wordDao()

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
}