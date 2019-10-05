package ru.coderedwolf.wordlearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.coderedwolf.wordlearn.database.entity.WordEntity

/**
 * @author CodeRedWolf. Date 23.09.2019.
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM WordEntity WHERE wordId  = :wordId")
    fun findOne(wordId: Long): Single<WordEntity>

    @Query("SELECT * FROM WordEntity WHERE categoryId = :categoryId")
    fun findAllByCategory(categoryId: Long): Single<List<WordEntity>>

    @Query("SELECT * FROM WordEntity WHERE categoryId = :categoryId")
    fun changesAllByCategory(categoryId: Long): Flowable<List<WordEntity>>

    @Insert
    fun save(wordEntity: WordEntity): Completable

    @Update
    fun update(wordEntity: WordEntity): Completable

    @Query("SELECT count(*) FROM WordEntity")
    fun count(): Flowable<Int>
}