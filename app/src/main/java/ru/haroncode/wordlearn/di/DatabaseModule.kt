package ru.haroncode.wordlearn.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import ru.haroncode.wordlearn.database.DataBase
import ru.haroncode.wordlearn.database.mapper.*

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
    fun provideWordSetDao(dataBase: DataBase) = dataBase.wordSetDao()

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
    fun provideWordSetMapper() = WordSetMapper()

    @Provides
    @JvmStatic
    @Singleton
    fun provideWordMapper() = WordMapper()
}
