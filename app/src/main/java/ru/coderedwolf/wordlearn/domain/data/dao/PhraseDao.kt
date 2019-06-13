package ru.coderedwolf.wordlearn.domain.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.coderedwolf.wordlearn.model.entity.PhraseEntity

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
@Dao
interface PhraseDao {

    @Query("select * from PhraseEntity where id = :phraseId")
    suspend fun findOne(phraseId: Long): PhraseEntity

    @Query("select * from PhraseEntity where topicId = :topicId")
    suspend fun findAllByTopic(topicId: Long): List<PhraseEntity>

    @Insert
    suspend fun save(phraseEntity: PhraseEntity): Long

    @Transaction
    suspend fun saveAndReturn(phraseEntity: PhraseEntity): PhraseEntity = findOne(save(phraseEntity))
}