package ru.coderedwolf.wordlearn.domain.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.coderedwolf.wordlearn.model.entity.WordEntity

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
@Dao
interface WordDao {

    @Query("select * from WordEntity where wordId  = :wordId")
    suspend fun findOne(wordId: Long): WordEntity

    @Query("select * from WordEntity where categoryId = :categoryId")
    suspend fun findAllByCategory(categoryId: Long): List<WordEntity>

    @Insert
    suspend fun save(wordEntity: WordEntity): Long

    @Insert
    suspend fun saveAll(list: List<WordEntity>)

    @Transaction
    suspend fun saveAndReturn(wordEntity: WordEntity): WordEntity {
        val wordId = save(wordEntity)
        return findOne(wordId)
    }

    @Transaction
    suspend fun incReview(wordId: Long) {
        val wordEntity = findOne(wordId)
        setReviewCount(wordId, wordEntity.reviewCount + 1)
    }

    @Query("update WordEntity set isStudy = :isStudy where wordId = :wordId")
    suspend fun setIsStudy(wordId: Long, isStudy: Boolean)

    @Query("update WordEntity set reviewCount = :reviewCount where wordId = :wordId")
    suspend fun setReviewCount(wordId: Long, reviewCount: Int)

    @Query("select count(*) from WordEntity")
    suspend fun count(): Int
}