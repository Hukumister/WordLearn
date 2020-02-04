package ru.coderedwolf.wordlearn.database.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import ru.coderedwolf.wordlearn.database.entity.PhraseTopicEntity
import ru.coderedwolf.wordlearn.database.entity.TopicAndPhrasesEntity

/**
 * @author HaronCode.
 */
@Dao
interface PhraseTopicDao {

    @Query("SELECT * FROM PhraseTopicEntity")
    fun findAll(): Single<List<PhraseTopicEntity>>

    @Query("SELECT * FROM PhraseTopicEntity WHERE id = :topicId")
    fun findOne(topicId: Long): Single<PhraseTopicEntity>

    @Insert
    fun save(phraseTopicEntity: PhraseTopicEntity): Completable

    @Delete
    fun remove(phraseTopicEntity: PhraseTopicEntity): Completable

    @Query("UPDATE PhraseTopicEntity SET isStudy = :isStudy WHERE id = :topicId")
    fun updateStudy(topicId: Long, isStudy: Boolean): Completable

    @Query("SELECT * FROM PhraseTopicEntity WHERE isStudy = 1")
    @Transaction
    fun findAllStudiedTopicAndPhrases(): Single<List<TopicAndPhrasesEntity>>

    @Query("SELECT count(*) FROM PhraseEntity")
    fun count(): Flowable<Int>
}