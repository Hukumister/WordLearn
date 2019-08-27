package ru.coderedwolf.learnword.learnphrasesflow.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.database.dao.PhraseTopicDao
import ru.coderedwolf.wordlearn.database.entity.TopicAndPhrasesEntity
import ru.coderedwolf.wordlearn.database.mapper.PhraseMapper
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper
import ru.coderedwolf.wordlearn.phrase.model.LearnPhrase
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
interface LearnPhraseRepository {
    suspend fun findLearnPhraseGroupByTopic(
        shuffled: Boolean = false
    ): Map<PhraseTopic, List<LearnPhrase>>
}

class LearnPhraseRepositoryImpl @Inject constructor(
    private val phraseTopicDao: PhraseTopicDao,
    private val phraseMapper: PhraseMapper,
    private val phraseTopicMapper: PhraseTopicMapper,
    private val dispatchersProvider: DispatchersProvider
) : LearnPhraseRepository {

    override suspend fun findLearnPhraseGroupByTopic(
        shuffled: Boolean
    ): Map<PhraseTopic, List<LearnPhrase>> = withContext(dispatchersProvider.io()) {
        phraseTopicDao.findAllStudiedTopicAndPhrases()
            .map { entity -> convertToPair(entity, shuffled) }
            .toMap()
    }

    private fun convertToPair(
        entity: TopicAndPhrasesEntity,
        shuffled: Boolean
    ): Pair<PhraseTopic, List<LearnPhrase>> {
        val topic = phraseTopicMapper.convert(entity.phraseTopic)

        val list = entity.phrases.map {
            phraseMapper.convertToLearn(entity.phraseTopic.title, it)
        }

        return topic to if (shuffled) list.shuffled() else list
    }
}