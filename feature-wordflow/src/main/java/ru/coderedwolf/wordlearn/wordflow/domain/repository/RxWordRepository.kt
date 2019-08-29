package ru.coderedwolf.wordlearn.wordflow.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.database.dao.WordDao
import ru.coderedwolf.wordlearn.database.mapper.WordMapper
import ru.coderedwolf.wordlearn.word.domain.repository.RxWordRepository
import ru.coderedwolf.wordlearn.word.model.Word
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 21.08.2019.
 */
class RxWordRepositoryImpl @Inject constructor(
        private val wordDao: WordDao,
        private val wordMapper: WordMapper,
        private val schedulerProvider: SchedulerProvider
) : RxWordRepository {

    override fun findAllPreviewByCategoryId(categoryId: Long) = Single
            .fromCallable { runBlocking { wordDao.findAllByCategory(categoryId) } }
            .map(wordMapper::convertListToPreview)
            .subscribeOn(schedulerProvider.io)

    override fun findAllByCategoryId(categoryId: Long) = Single
            .fromCallable { runBlocking { wordDao.findAllByCategory(categoryId) } }
            .map(wordMapper::convertList)
            .subscribeOn(schedulerProvider.io)

    override fun save(word: Word) = word
            .let(wordMapper::convertToEntity)
            .let { entity -> Completable.fromAction { runBlocking { wordDao.save(entity) } } }
            .subscribeOn(schedulerProvider.io)

    override fun count() = Flowable.fromCallable { runBlocking { wordDao.count() } }
            .subscribeOn(schedulerProvider.io)

}