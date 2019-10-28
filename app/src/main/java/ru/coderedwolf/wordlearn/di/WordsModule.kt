package ru.coderedwolf.wordlearn.di

import dagger.Binds
import dagger.Module
import ru.coderedwolf.wordlearn.mainflow.domain.repository.WordsCategoryRepository
import ru.coderedwolf.wordlearn.mainflow.domain.repository.WordsCategoryRepositoryImpl
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import ru.coderedwolf.wordlearn.wordflow.domain.repository.WordRepositoryImpl
import javax.inject.Singleton

@Module
interface WordsModule {
    @Binds
    @Singleton
    fun provideWordRepository(
            wordRepositoryImpl: WordRepositoryImpl
    ): WordRepository

    @Binds
    @Singleton
    fun provideWordsCategoryRepository(
            wordsCategoryRepositoryImpl: WordsCategoryRepositoryImpl
    ): WordsCategoryRepository

}