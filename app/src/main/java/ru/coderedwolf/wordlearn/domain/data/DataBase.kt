package ru.coderedwolf.wordlearn.domain.data

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.coderedwolf.wordlearn.domain.data.dao.WordsCategoryDao
import ru.coderedwolf.wordlearn.model.entity.CategoryEntity

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
@Database(entities = [CategoryEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun wordsCategoryDao(): WordsCategoryDao
}