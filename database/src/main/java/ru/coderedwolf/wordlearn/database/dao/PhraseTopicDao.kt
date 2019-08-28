package ru.coderedwolf.wordlearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.coderedwolf.wordlearn.database.entity.PhraseTopicEntity
import ru.coderedwolf.wordlearn.database.entity.TopicAndPhrasesEntity

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
@Dao
interface PhraseTopicDao {

    @Query("select * from PhraseTopicEntity")
    suspend fun findAll(): List<PhraseTopicEntity>

    @Query("select * from PhraseTopicEntity where id = :topicId")
    suspend fun findById(topicId: Long): PhraseTopicEntity

    @Insert
    suspend fun save(phraseTopicEntity: PhraseTopicEntity): Long

    @Transaction
    suspend fun saveAndReturn(phraseTopicEntity: PhraseTopicEntity): PhraseTopicEntity =
        findById(save(phraseTopicEntity))

    @Query("delete from PhraseTopicEntity where id = :topicId")
    suspend fun remove(topicId: Long)

    @Query("update PhraseTopicEntity set isStudy = :isStudy where id = :topicId")
    suspend fun updateStudy(topicId: Long, isStudy: Boolean)

    @Query("select * from PhraseTopicEntity where isStudy = 1")
    @Transaction
    fun findAllStudiedTopicAndPhrases(): List<TopicAndPhrasesEntity>

    @Query("select count(*) from PhraseEntity")
    suspend fun count(): Int
}