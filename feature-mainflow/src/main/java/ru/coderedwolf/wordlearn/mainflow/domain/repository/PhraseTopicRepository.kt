package ru.coderedwolf.wordlearn.mainflow.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import ru.coderedwolf.wordlearn.database.dao.PhraseTopicDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
interface PhraseTopicRepository {
    fun updateStudy(topicId: Long, isStudy: Boolean): Completable
    fun findAll(): Single<List<PhraseTopic>>
    fun save(phraseTopic: PhraseTopic): Completable
}

class PhraseTopicRepositoryImpl @Inject constructor(
        private val phraseTopicDao: PhraseTopicDao,
        private val phraseTopicMapper: PhraseTopicMapper
) : PhraseTopicRepository {

    override fun updateStudy(topicId: Long, isStudy: Boolean) = phraseTopicDao
            .updateStudy(topicId, isStudy)

    override fun findAll() = phraseTopicDao.findAll()
            .map(phraseTopicMapper::convertList)

    override fun save(phraseTopic: PhraseTopic) = phraseTopic
            .let(phraseTopicMapper::convertToEntity)
            .let(phraseTopicDao::save)

}