package ru.coderedwolf.wordlearn.domain.reporitory

import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.PhraseMapper
import ru.coderedwolf.wordlearn.domain.mappers.PhraseTopicMapper
import ru.coderedwolf.wordlearn.model.phrase.Phrase
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic
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
        dateBase: DataBase,
        private val phraseTopicMapper: PhraseTopicMapper,
        private val phraseMapper: PhraseMapper
) : PrePopulatePhraseRepository {

    private val phraseTopicDao = dateBase.phraseTopicDao()
    private val phraseDao = dateBase.phraseDao()

    override suspend fun phraseCount(): Int = phraseDao.count()

    override suspend fun saveTopic(phraseTopic: PhraseTopic): PhraseTopic {
        val topicEntity = phraseTopicDao.saveAndReturn(phraseTopicMapper.convertToEntity(phraseTopic))
        return phraseTopicMapper.convert(topicEntity)
    }

    override suspend fun saveAllPhrases(phrases: List<Phrase>) {
        phraseDao.saveAll(phrases.map { phraseMapper.convertToEntity(it) })
    }
}