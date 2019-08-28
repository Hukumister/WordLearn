package ru.coderedwolf.learnword.learnphrasesflow.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.database.dao.PhraseDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseMapper
import ru.coderedwolf.wordlearn.phrase.model.Phrase
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
    private val phraseDao: PhraseDao,
    private val phraseMapper: PhraseMapper,
    private val dispatchersProvider: DispatchersProvider
) : PhraseRepository {

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