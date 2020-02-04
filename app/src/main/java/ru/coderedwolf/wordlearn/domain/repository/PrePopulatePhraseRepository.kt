package ru.coderedwolf.wordlearn.domain.repository

import ru.coderedwolf.wordlearn.database.dao.PhraseDao
import ru.coderedwolf.wordlearn.database.dao.PhraseTopicDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseMapper
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper
import ru.coderedwolf.wordlearn.phrase.model.Phrase
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author HaronCode. Date 16.06.2019.
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
    override suspend fun phraseCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveTopic(phraseTopic: PhraseTopic): PhraseTopic {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun saveAllPhrases(phrases: List<Phrase>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}