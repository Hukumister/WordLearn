package ru.coderedwolf.wordlearn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.coderedwolf.wordlearn.database.converter.DateConverter
import ru.coderedwolf.wordlearn.database.dao.*
import ru.coderedwolf.wordlearn.database.entity.PhraseEntity
import ru.coderedwolf.wordlearn.database.entity.PhraseTopicEntity
import ru.coderedwolf.wordlearn.database.entity.WordCategoryEntity
import ru.coderedwolf.wordlearn.database.entity.WordEntity

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
@Database(
    entities = [
        WordCategoryEntity::class,
        WordEntity::class,
        PhraseTopicEntity::class,
        PhraseEntity::class
    ],
    version = 1
)
@TypeConverters(
    value = [
        DateConverter::class
    ]
)
abstract class DataBase : RoomDatabase() {
    abstract fun wordsCategoryDao(): WordsCategoryDao
    abstract fun categoryAndWordDao(): CategoryAndWordDao
    abstract fun wordDao(): WordDao
    abstract fun phraseDao(): PhraseDao
    abstract fun phraseTopicDao(): PhraseTopicDao
}