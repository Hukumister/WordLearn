package ru.coderedwolf.wordlearn.domain.reporitory

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.PhraseMapper
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.model.phrase.Phrase
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 15.06.2019.
 */
interface PhraseRepository {

    suspend fun findOne(phraseId: Long): Phrase

    suspend fun findAllByTopicId(topicId: Long): List<Phrase>

    suspend fun save(phrase: Phrase): Phrase

    suspend fun saveAll(phraseList: List<Phrase>)

    suspend fun update(phrase: Phrase)
}

class PhraseRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val phraseMapper: PhraseMapper,
        private val dispatchersProvider: DispatchersProvider
) : PhraseRepository {

    private val phraseDao = dataBase.phraseDao()

    override suspend fun findOne(phraseId: Long): Phrase = withContext(dispatchersProvider.io()) {
        phraseMapper.convert(phraseDao.findOne(phraseId))
    }

    override suspend fun findAllByTopicId(topicId: Long): List<Phrase> = withContext(dispatchersProvider.io()) {
        phraseDao.findAllByTopic(topicId).map { phraseMapper.convert(it) }
    }

    override suspend fun save(phrase: Phrase): Phrase = withContext(dispatchersProvider.io()) {
        val entity = phraseMapper.convertToEntity(phrase)
        phraseMapper.convert(phraseDao.saveAndReturn(entity))
    }

    override suspend fun update(phrase: Phrase) = withContext(dispatchersProvider.io()) {
        phraseDao.update(phraseMapper.convertToEntity(phrase))
    }

    override suspend fun saveAll(phraseList: List<Phrase>) = withContext(dispatchersProvider.io()) {
        phraseDao.saveAll(phraseList.map { phraseMapper.convertToEntity(it) })
    }
}