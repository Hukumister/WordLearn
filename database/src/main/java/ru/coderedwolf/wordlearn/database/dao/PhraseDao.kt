package ru.coderedwolf.wordlearn.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.coderedwolf.wordlearn.database.entity.PhraseEntity

/**
 * @author HaronCode. Date 14.06.2019.
 */
@Dao
interface PhraseDao {

    @Query("SELECT * FROM PhraseEntity WHERE id = :phraseId")
    fun findOne(phraseId: Long): Single<PhraseEntity>

    @Query("SELECT * FROM PhraseEntity WHERE topicId = :topicId")
    fun findAllByTopic(topicId: Long): Single<List<PhraseEntity>>

    @Insert
    fun save(phraseEntity: PhraseEntity): Completable

    @Update
    fun update(phraseEntity: PhraseEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(phraseEntityList: List<PhraseEntity>): Completable

    @Query("select count(*) from PhraseEntity")
    fun count(): Flowable<Int>
}