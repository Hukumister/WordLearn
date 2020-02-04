package ru.haroncode.api.wordset.domain.repository

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.haroncode.api.wordset.model.WordSet

/**
 * @author HaronCode. Date 04.11.2019.
 */
interface WordSetRepository {

    fun findAll(): Single<List<WordSet>>
    fun findOne(id: Long): Maybe<WordSet>

    fun findAllUserSet(): Single<List<WordSet>>
    fun observableAllUserSet(): Flowable<List<WordSet>>
}
