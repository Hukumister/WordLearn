package ru.coderedwolf.wordlearn.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.coderedwolf.wordlearn.database.converter.DateConverter
import ru.coderedwolf.wordlearn.database.dao.*
import ru.coderedwolf.wordlearn.database.entity.*

/**
 * @author HaronCode. Date 04.05.2019.
 */
@Database(
    entities = [
        WordCategoryEntity::class,
        WordEntity::class,
        PhraseTopicEntity::class,
        PhraseEntity::class,
        WordSetEntity::class
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
    abstract fun wordDao(): WordDao
    abstract fun phraseDao(): PhraseDao
    abstract fun wordSetDao(): WordSetDao
    abstract fun phraseTopicDao(): PhraseTopicDao
}