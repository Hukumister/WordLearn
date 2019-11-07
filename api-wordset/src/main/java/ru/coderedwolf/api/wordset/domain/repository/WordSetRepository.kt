package ru.coderedwolf.api.wordset.domain.repository

import io.reactivex.Maybe
import io.reactivex.Single
import ru.coderedwolf.api.wordset.model.WordSet

/**
 * @author CodeRedWolf. Date 04.11.2019.
 */
interface WordSetRepository {

    fun findAll(): Single<List<WordSet>>
    fun findOne(id: Long): Maybe<WordSet>

    fun findAllUserSet(): Single<List<WordSet>>

}