package ru.haroncode.wordlearn.di

import dagger.Binds
import dagger.Module
import javax.inject.Singleton
import ru.haroncode.wordlearn.mainflow.domain.repository.WordsCategoryRepository
import ru.haroncode.wordlearn.mainflow.domain.repository.WordsCategoryRepositoryImpl
import ru.haroncode.wordlearn.word.domain.repository.WordRepository
import ru.haroncode.wordlearn.wordflow.domain.repository.WordRepositoryImpl

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
