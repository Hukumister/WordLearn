package ru.coderedwolf.wordlearn.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import de.siegmar.fastcsv.reader.CsvReader
import ru.coderedwolf.wordlearn.domain.interactors.PrePopulateDataBaseInteractor
import ru.coderedwolf.wordlearn.domain.interactors.PrePopulateDataBaseInteractorImpl
import ru.coderedwolf.wordlearn.domain.repository.*
import javax.inject.Singleton

@Module
abstract class PrepopulateModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideCsvReader() = CsvReader().apply {
            setFieldSeparator(';')
        }
    }

    @Binds
    @Singleton
    abstract fun providePhraseAssetRepository(
        phraseAssetRepositoryImpl: PhraseAssetRepositoryImpl
    ): PhraseAssetRepository

    @Binds
    @Singleton
    abstract fun provideWordAssetRepository(
        wordAssetRepositoryImpl: WordAssetRepositoryImpl
    ): WordAssetRepository

    @Binds
    @Singleton
    abstract fun providePrePopulatePhraseRepository(
        prePopulatePhraseRepositoryImpl: PrePopulatePhraseRepositoryImpl
    ): PrePopulatePhraseRepository

    @Binds
    @Singleton
    abstract fun providePrePopulateDataBaseInteractor(
        prePopulateDataBaseInteractorImpl: PrePopulateDataBaseInteractorImpl
    ): PrePopulateDataBaseInteractor
}