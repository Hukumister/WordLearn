package ru.coderedwolf.learnword.learnphrasesflow.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.database.dao.PhraseDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseMapper
import ru.coderedwolf.wordlearn.phrase.model.Phrase
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 15.06.2019.
 */
interface PhraseRepository {
    fun findOne(phraseId: Long): Single<Phrase>
    fun findAllByTopicId(topicId: Long): Single<List<Phrase>>
    fun save(phrase: Phrase): Completable
    fun saveAll(phraseList: List<Phrase>): Completable
    fun update(phrase: Phrase): Completable
}

class PhraseRepositoryImpl @Inject constructor(
        private val phraseDao: PhraseDao,
        private val phraseMapper: PhraseMapper,
        private val schedulerProvider: SchedulerProvider
) : PhraseRepository {

    override fun findOne(phraseId: Long) = phraseDao.findOne(phraseId)
            .map(phraseMapper::convert)
            .subscribeOn(schedulerProvider.io)

    override fun findAllByTopicId(topicId: Long) = phraseDao.findAllByTopic(topicId)
            .map(phraseMapper::convertList)
            .subscribeOn(schedulerProvider.io)

    override fun save(phrase: Phrase) = phrase
            .let(phraseMapper::convertToEntity)
            .let(phraseDao::save)
            .subscribeOn(schedulerProvider.io)

    override fun update(phrase: Phrase) = phrase
            .let(phraseMapper::convertToEntity)
            .let(phraseDao::update)
            .subscribeOn(schedulerProvider.io)

    override fun saveAll(phraseList: List<Phrase>) = phraseList
            .let(phraseMapper::convertListToEntity)
            .let(phraseDao::saveAll)
            .subscribeOn(schedulerProvider.io)
}