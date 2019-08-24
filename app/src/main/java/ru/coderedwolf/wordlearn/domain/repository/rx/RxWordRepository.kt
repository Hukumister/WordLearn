package ru.coderedwolf.wordlearn.domain.repository.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.mappers.WordMapper
import ru.coderedwolf.wordlearn.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.model.word.Word
import ru.coderedwolf.wordlearn.model.word.WordPreview
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 21.08.2019.
 */
/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
interface RxWordRepository {

    fun findAllPreviewByCategoryId(categoryId: Long): Single<List<WordPreview>>

    fun findAllByCategoryId(categoryId: Long): Single<List<Word>>

    fun save(word: Word): Completable

    fun count(): Flowable<Int>

}

class RxWordRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val wordMapper: WordMapper,
        private val schedulerProvider: SchedulerProvider
) : RxWordRepository {

    private val wordDao = dataBase.wordDao()

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