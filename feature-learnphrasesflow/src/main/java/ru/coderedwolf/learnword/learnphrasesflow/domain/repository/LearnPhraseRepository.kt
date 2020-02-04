package ru.coderedwolf.learnword.learnphrasesflow.domain.repository

import ru.coderedwolf.wordlearn.phrase.model.LearnPhrase
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author HaronCode. Date 14.06.2019.
 */
interface LearnPhraseRepository {
    suspend fun findLearnPhraseGroupByTopic(
            shuffled: Boolean = false
    ): Map<PhraseTopic, List<LearnPhrase>>
}

class MockLearnPhraseRepository @Inject constructor() : LearnPhraseRepository {
    override suspend fun findLearnPhraseGroupByTopic(shuffled: Boolean): Map<PhraseTopic, List<LearnPhrase>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
/*
class LearnPhraseRepositoryImpl @Inject constructor(
    private val phraseTopicDao: PhraseTopicDao,
    private val phraseMapper: PhraseMapper,
    private val phraseTopicMapper: PhraseTopicMapper,
    private val dispatchersProvider: DispatchersProvider
) : LearnPhraseRepository {

    override suspend fun findLearnPhraseGroupByTopic(
        shuffled: Boolean
    ): Map<PhraseTopic, List<LearnPhrase>> =

  */
/*  private fun convertToPair(
        entity: TopicAndPhrasesEntity,
        shuffled: Boolean
    ): Pair<PhraseTopic, List<LearnPhrase>> {
        val topic = phraseTopicMapper.convert(entity.phraseTopic)

        val list = entity.phrases.map {
            phraseMapper.convertToLearn(entity.phraseTopic.title, it)
        }

        return topic to if (shuffled) list.shuffled() else list
    }*//*

}*/
