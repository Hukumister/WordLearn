package ru.coderedwolf.wordlearn.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.coderedwolf.wordlearn.database.entity.CategoryAndWordEntity

/**
 * @author CodeRedWolf. Date 26.06.2019.
 */
@Dao
interface CategoryAndWordDao {

    @Query(
        """
        select * from WordCategoryEntity as c 
            join WordEntity as w on w.categoryId = c.id and c.isStudy = 1 and w.isStudy > 0 and w.reviewCount > 1
            limit :limit
            """
    )
    @Transaction
    suspend fun findAllMemberCategoryAndWordEntity(limit: Int): List<CategoryAndWordEntity>

    @Query(
        """
        select * from WordCategoryEntity as c 
            join WordEntity as w on w.categoryId = c.id and c.isStudy = 1 and w.isStudy = 1 and w.reviewCount = 0
            limit :limit
            """
    )
    @Transaction
    suspend fun findAllNewCategoryAndWordEntity(limit: Int): List<CategoryAndWordEntity>
}