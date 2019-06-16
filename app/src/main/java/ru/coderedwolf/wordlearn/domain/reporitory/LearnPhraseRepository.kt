package ru.coderedwolf.wordlearn.domain.reporitory

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

    suspend fun findLearnPhraseGroupByTopic(): Map<PhraseTopic, List<LearnPhrase>>
}

class LearnPhraseRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val phraseMapper: PhraseMapper,
        private val phraseTopicMapper: PhraseTopicMapper,
        private val dispatchersProvider: DispatchersProvider
) : LearnPhraseRepository {

    private val phraseTopicDao = dataBase.phraseTopicDao()

    override suspend fun findLearnPhraseGroupByTopic(): Map<PhraseTopic, List<LearnPhrase>> = withContext(dispatchersProvider.io()) {
        phraseTopicDao.findAllStudiedTopicAndPhrases()
                .map { entity -> convertToPair(entity) }
                .toMap()
    }

    private fun convertToPair(entity: TopicAndPhrasesEntity) =
            phraseTopicMapper.convert(entity.phraseTopic) to entity.phrases.map {
                phraseMapper.convertToLearn(entity.phraseTopic.title, it)
            }

    /*mapOf(
            PhraseTopic(1, "title", true) to listOf(
                LearnPhrase(1, "title", "text1", "translate"),
                LearnPhrase(1, "title", "text2", "translate"),
                LearnPhrase(1, "title", "text3", "translate"),
                LearnPhrase(1, "title", "text4", "translate"),
                LearnPhrase(1, "title", "text5", "translate")
            )
        )*/
}