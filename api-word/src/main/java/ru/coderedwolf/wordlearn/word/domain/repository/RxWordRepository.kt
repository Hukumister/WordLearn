package ru.coderedwolf.wordlearn.word.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.word.model.WordPreview

/**
 * @author CodeRedWolf. Date 29.08.2019.
 */
interface RxWordRepository {
    fun findAllPreviewByCategoryId(categoryId: Long): Single<List<WordPreview>>
    fun findAllByCategoryId(categoryId: Long): Single<List<Word>>
    fun save(word: Word): Completable
    fun count(): Flowable<Int>
}