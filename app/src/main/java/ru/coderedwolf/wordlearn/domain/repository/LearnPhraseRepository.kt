package ru.coderedwolf.wordlearn.domain.repository

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.PhraseMapper
import ru.coderedwolf.wordlearn.domain.mappers.PhraseTopicMapper
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.model.entity.TopicAndPhrasesEntity
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic
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
        dataBase: DataBase,
        private val phraseMapper: PhraseMapper,
        private val phraseTopicMapper: PhraseTopicMapper,
        private val dispatchersProvider: DispatchersProvider
) : LearnPhraseRepository {

    private val phraseTopicDao = dataBase.phraseTopicDao()

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