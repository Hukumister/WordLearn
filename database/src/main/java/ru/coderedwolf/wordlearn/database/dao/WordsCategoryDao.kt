package ru.coderedwolf.wordlearn.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import ru.coderedwolf.wordlearn.database.entity.WordCategoryEntity

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
@Dao
interface WordsCategoryDao {

    @Query("select * from WordCategoryEntity")
    suspend fun findAll(): List<WordCategoryEntity>

    @Query("select * from WordCategoryEntity where id=:categoryId")
    suspend fun findById(categoryId: Long): WordCategoryEntity

    @Insert
    suspend fun save(wordCategoryEntity: WordCategoryEntity): Long

    @Transaction
    suspend fun saveAndReturn(wordCategoryEntity: WordCategoryEntity): WordCategoryEntity {
        val insertedId = save(wordCategoryEntity)
        return findById(insertedId)
    }

    @Query("delete from WordCategoryEntity where id = :categoryId")
    suspend fun remove(categoryId: Long)
}