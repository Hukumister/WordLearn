package ru.coderedwolf.wordlearn.domain.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.coderedwolf.wordlearn.model.entity.CategoryAndWordEntity

/**
 * @author CodeRedWolf. Date 26.06.2019.
 */
@Dao
interface CategoryAndWordDao {

    @Query("""
        select * from WordCategoryEntity as c 
            join WordEntity as w 
            where c.isStudy = 1 and w.isStudy = 1 and w.reviewCount > 1
            """)
    @Transaction
    suspend fun findAllMemberCategoryAndWordEntity(limit: Int): List<CategoryAndWordEntity>

    @Query("""
        select * from WordCategoryEntity as c 
            join WordEntity as w 
            where c.isStudy = 1 and w.isStudy = 1 and w.reviewCount = 0
            """)
    @Transaction
    suspend fun findAllNewCategoryAndWordEntity(limit: Int): List<CategoryAndWordEntity>
}