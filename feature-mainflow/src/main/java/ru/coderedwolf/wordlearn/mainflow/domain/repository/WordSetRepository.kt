package ru.coderedwolf.wordlearn.mainflow.domain.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.coderedwolf.api.wordset.domain.repository.WordSetRepository
import ru.coderedwolf.api.wordset.model.WordSet
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.database.dao.WordSetDao
import ru.coderedwolf.wordlearn.database.mapper.WordSetMapper
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.11.2019.
 */
class WordSetRepositoryImpl @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val wordSetDao: WordSetDao,
    private val wordSetMapper: WordSetMapper
) : WordSetRepository {

    override fun findAll(): Single<List<WordSet>> = wordSetDao
        .findAll()
        .subscribeOn(schedulerProvider.io)
        .map(wordSetMapper::convertList)

    override fun findOne(id: Long): Maybe<WordSet> = wordSetDao
        .findOne(id)
        .subscribeOn(schedulerProvider.io)
        .map(wordSetMapper::convertToModel)

    override fun findAllUserSet(): Single<List<WordSet>> = wordSetDao
        .findAllFilterUserSet(isUserSet = true)
        .subscribeOn(schedulerProvider.io)
        .map(wordSetMapper::convertList)

    override fun observableAllUserSet(): Flowable<List<WordSet>> = wordSetDao
        .observableAllFilterUserSet(isUserSet = true)
        .subscribeOn(schedulerProvider.io)
        .map(wordSetMapper::convertList)

}