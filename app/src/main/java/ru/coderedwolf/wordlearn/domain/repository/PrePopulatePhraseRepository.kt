package ru.coderedwolf.wordlearn.domain.repository

import ru.coderedwolf.wordlearn.database.dao.PhraseDao
import ru.coderedwolf.wordlearn.database.dao.PhraseTopicDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseMapper
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper
import ru.coderedwolf.wordlearn.phrase.model.Phrase
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
interface PrePopulatePhraseRepository {
    suspend fun phraseCount(): Int
    suspend fun saveTopic(phraseTopic: PhraseTopic): PhraseTopic
     suspend fun saveAllPhrases(phrases: List<Phrase>)
}

class PrePopulatePhraseRepositoryImpl @Inject constructor(
    private val phraseTopicDao: PhraseTopicDao,
    private val phraseDao: PhraseDao,
    private val phraseTopicMapper: PhraseTopicMapper,
    private val phraseMapper: PhraseMapper
) : PrePopulatePhraseRepository {

    override suspend fun phraseCount(): Int = phraseDao.count()

    override suspend fun saveTopic(phraseTopic: PhraseTopic): PhraseTopic {
        val topicEntity = phraseTopicDao.saveAndReturn(phraseTopicMapper.convertToEntity(phraseTopic))
        return phraseTopicMapper.convert(topicEntity)
    }

    override suspend fun saveAllPhrases(phrases: List<Phrase>) {
        phraseDao.saveAll(phrases.map { phraseMapper.convertToEntity(it) })
    }
}