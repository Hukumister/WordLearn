package ru.haroncode.wordlearn.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.haroncode.wordlearn.database.entity.WordSetEntity

/**
 * @author HaronCode.
 */
@Dao
interface WordSetDao {

    @Query("SELECT * FROM WordSetEntity")
    fun findAll(): Single<List<WordSetEntity>>

    @Query("SELECT * FROM WordSetEntity WHERE id = :id")
    fun findOne(id: Long): Maybe<WordSetEntity>

    @Query("SELECT * FROM WordSetEntity WHERE isUserSet = :isUserSet")
    fun findAllFilterUserSet(isUserSet: Boolean): Single<List<WordSetEntity>>

    @Query("SELECT * FROM WordSetEntity WHERE isUserSet = :isUserSet")
    fun observableAllFilterUserSet(isUserSet: Boolean): Flowable<List<WordSetEntity>>
}
