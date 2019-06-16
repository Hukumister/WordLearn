package ru.coderedwolf.wordlearn.di.module

import ru.coderedwolf.wordlearn.domain.interactors.word.WordInteractor
import ru.coderedwolf.wordlearn.domain.interactors.word.WordInteractorImpl
import ru.coderedwolf.wordlearn.domain.interactors.word.category.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.domain.interactors.word.category.WordsCategoryInteractorImpl
import ru.coderedwolf.wordlearn.domain.mappers.CategoryMapper
import ru.coderedwolf.wordlearn.domain.reporitory.WordRepository
import ru.coderedwolf.wordlearn.domain.reporitory.WordRepositoryImpl
import ru.coderedwolf.wordlearn.domain.reporitory.WordsCategoryRepository
import ru.coderedwolf.wordlearn.domain.reporitory.WordsCategoryRepositoryImpl
import toothpick.config.Module

/**
 * @author CodeRedWolf. Date 08.05.2019.
 */
class DataModule : Module() {
    init {

        //init
        bind(PrePopulateDataBaseInteractor::class.java).to(PrePopulateDataBaseInteractorImpl::class.java)
        bind(PrePopulatePhraseRepository::class.java).to(PrePopulatePhraseRepositoryImpl::class.java)
        bind(PhraseAssetRepository::class.java).to(PhraseAssetRepositoryImpl::class.java)
        bind(CsvReader::class.java).toProvider(CsvReaderProvider::class.java)

        //Category words
        bind(WordsCategoryRepository::class.java).to(WordsCategoryRepositoryImpl::class.java)
        bind(WordsCategoryInteractor::class.java).to(WordsCategoryInteractorImpl::class.java)
        bind(CategoryMapper::class.java).singletonInScope()

        //Word
        bind(WordRepository::class.java).to(WordRepositoryImpl::class.java)
        bind(WordInteractor::class.java).to(WordInteractorImpl::class.java)
    }
}