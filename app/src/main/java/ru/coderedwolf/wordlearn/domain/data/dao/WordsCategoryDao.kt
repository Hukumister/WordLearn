package ru.coderedwolf.wordlearn.domain.data.dao

import androidx.room.*
import ru.coderedwolf.wordlearn.model.entity.CategoryEntity

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
@Dao
interface WordsCategoryDao {

    @Query("select * from categoryentity")
    suspend fun findAll(): List<CategoryEntity>

    suspend fun findById(categoryId: Long): CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categoryEntity: CategoryEntity): Long

    @Transaction
    suspend fun saveAndReturn(categoryEntity: CategoryEntity): CategoryEntity {
        val insertedId = save(categoryEntity)
        return findById(insertedId)
    }

    @Query("delete from categoryentity where id = :categoryId")
    suspend fun remove(categoryId: Long)
}