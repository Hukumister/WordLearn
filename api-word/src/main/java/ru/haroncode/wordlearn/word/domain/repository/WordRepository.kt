package ru.haroncode.wordlearn.word.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.haroncode.wordlearn.word.model.Word
import ru.haroncode.wordlearn.word.model.WordPreview

/**
 * @author HaronCode. Date 29.08.2019.
 */
interface WordRepository {
    fun findAllPreviewByCategoryId(categoryId: Long): Single<List<WordPreview>>
    fun findAllByCategoryId(categoryId: Long): Single<List<Word>>
    fun save(word: Word): Completable
    fun count(): Flowable<Int>
}