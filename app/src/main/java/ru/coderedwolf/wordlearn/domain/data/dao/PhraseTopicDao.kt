package ru.coderedwolf.wordlearn.domain.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.coderedwolf.wordlearn.model.entity.PhraseTopicEntity

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
}