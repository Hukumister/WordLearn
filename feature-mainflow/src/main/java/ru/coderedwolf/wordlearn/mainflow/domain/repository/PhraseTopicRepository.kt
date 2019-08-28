package ru.coderedwolf.wordlearn.mainflow.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.database.dao.PhraseTopicDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
interface PhraseTopicRepository {
    suspend fun updateStudy(topicId: Long, isStudy: Boolean)
    suspend fun findAll(): List<PhraseTopic>
    suspend fun save(phraseTopic: PhraseTopic): PhraseTopic
}

class PhraseTopicRepositoryImpl @Inject constructor(
    private val phraseTopicDao: PhraseTopicDao,
    private val phraseTopicMapper: PhraseTopicMapper,
    private val dispatchersProvider: DispatchersProvider
) : PhraseTopicRepository {

    override suspend fun save(phraseTopic: PhraseTopic): PhraseTopic = withContext(dispatchersProvider.io()) {
        val entity = phraseTopicMapper.convertToEntity(phraseTopic)
        phraseTopicMapper.convert(phraseTopicDao.saveAndReturn(entity))
    }

    override suspend fun updateStudy(topicId: Long, isStudy: Boolean) = withContext(dispatchersProvider.io()) {
        phraseTopicDao.updateStudy(topicId, isStudy)
    }

    override suspend fun findAll(): List<PhraseTopic> = withContext(dispatchersProvider.io()) {
        phraseTopicDao.findAll().map { phraseTopicMapper.convert(it) }
    }
}