package ru.coderedwolf.wordlearn.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.coderedwolf.wordlearn.database.DataBase
import ru.coderedwolf.wordlearn.database.mapper.CategoryMapper
import ru.coderedwolf.wordlearn.database.mapper.PhraseMapper
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper
import ru.coderedwolf.wordlearn.database.mapper.WordMapper
import javax.inject.Singleton

@Module
object DatabaseModule {
    private const val DATA_BASE_NAME = "WordLearnDataBase"

    @Provides
    @JvmStatic
    @Singleton
    fun provideDatabase(context: Context) =
        Room.databaseBuilder(context, DataBase::class.java, DATA_BASE_NAME).build()

    @Provides
    @JvmStatic
    @Singleton
    fun provideWordsCategoryDao(dataBase: DataBase) = dataBase.wordsCategoryDao()

    @Provides
    @JvmStatic
    @Singleton
    fun provideCategoryAndWordDao(dataBase: DataBase) = dataBase.categoryAndWordDao()

    @Provides
    @JvmStatic
    @Singleton
    fun provideWordDao(dataBase: DataBase) = dataBase.wordDao()

    @Provides
    @JvmStatic
    @Singleton
    fun providePhraseDao(dataBase: DataBase) = dataBase.phraseDao()

    @Provides
    @JvmStatic
    @Singleton
    fun providePhraseTopicDao(dataBase: DataBase) = dataBase.phraseTopicDao()

    @Provides
    @JvmStatic
    @Singleton
    fun provideCategoryMapper() = CategoryMapper()

    @Provides
    @JvmStatic
    @Singleton
    fun providePhraseMapper() = PhraseMapper()

    @Provides
    @JvmStatic
    @Singleton
    fun providePhraseTopicMapper() = PhraseTopicMapper()

    @Provides
    @JvmStatic
    @Singleton
    fun provideWordMapper() = WordMapper()
}