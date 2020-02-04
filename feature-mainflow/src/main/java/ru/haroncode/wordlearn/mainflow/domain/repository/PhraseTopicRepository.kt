package ru.haroncode.wordlearn.mainflow.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import ru.haroncode.wordlearn.database.dao.PhraseTopicDao
import ru.haroncode.wordlearn.database.mapper.PhraseTopicMapper
import ru.haroncode.wordlearn.phrase.model.PhraseTopic

/**
 * @author HaronCode. Date 16.06.2019.
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
