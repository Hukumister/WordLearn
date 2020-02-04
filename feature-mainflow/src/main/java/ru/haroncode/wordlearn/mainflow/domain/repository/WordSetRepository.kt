package ru.haroncode.wordlearn.mainflow.domain.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.haroncode.api.wordset.domain.repository.WordSetRepository
import ru.haroncode.api.wordset.model.WordSet
import ru.haroncode.wordlearn.common.domain.system.SchedulerProvider
import ru.haroncode.wordlearn.database.dao.WordSetDao
import ru.haroncode.wordlearn.database.mapper.WordSetMapper
import javax.inject.Inject

/**
 * @author HaronCode. Date 04.11.2019.
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