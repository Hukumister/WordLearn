package ru.coderedwolf.wordlearn.domain.data.dao

import androidx.room.Dao
import androidx.room.Query
import ru.coderedwolf.wordlearn.model.entity.WordEntity

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
@Dao
interface WordDao {

    @Query("select * from WordEntity where categoryId = :categoryId")
    suspend fun findAllByCategory(categoryId: Long): List<WordEntity>
}