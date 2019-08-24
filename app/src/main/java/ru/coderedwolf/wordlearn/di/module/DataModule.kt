package ru.coderedwolf.wordlearn.di.module

import de.siegmar.fastcsv.reader.CsvReader
import ru.coderedwolf.wordlearn.di.BatchSizeMemorizedWord
import ru.coderedwolf.wordlearn.di.BatchSizeNewWord
import ru.coderedwolf.wordlearn.di.PrimitiveWrapper
import ru.coderedwolf.wordlearn.di.provider.CsvReaderProvider
import ru.coderedwolf.wordlearn.domain.interactors.init.PrePopulateDataBaseInteractor
import ru.coderedwolf.wordlearn.domain.interactors.init.PrePopulateDataBaseInteractorImpl
import ru.coderedwolf.wordlearn.domain.interactors.learn.LearnPhraseInteractor
import ru.coderedwolf.wordlearn.domain.interactors.learn.LearnPhraseInteractorImpl
import ru.coderedwolf.wordlearn.domain.interactors.learn.LearnWordsInteractor
import ru.coderedwolf.wordlearn.domain.interactors.learn.LearnWordsInteractorImpl
import ru.coderedwolf.wordlearn.domain.interactors.phrase.PhraseTopicInteractor
import ru.coderedwolf.wordlearn.domain.interactors.phrase.PhraseTopicInteractorImpl
import ru.coderedwolf.wordlearn.domain.interactors.rx.word.RxWordInteractor
import ru.coderedwolf.wordlearn.domain.interactors.rx.word.RxWordInteractorImpl
import ru.coderedwolf.wordlearn.domain.interactors.word.WordInteractor
import ru.coderedwolf.wordlearn.domain.interactors.word.WordInteractorImpl
import ru.coderedwolf.wordlearn.domain.interactors.word.category.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.domain.interactors.word.category.WordsCategoryInteractorImpl
import ru.coderedwolf.wordlearn.domain.mappers.CategoryMapper
import ru.coderedwolf.wordlearn.domain.repository.*
import ru.coderedwolf.wordlearn.domain.repository.rx.RxWordRepository
import ru.coderedwolf.wordlearn.domain.repository.rx.RxWordRepositoryImpl
import toothpick.config.Module

/**
 * @author CodeRedWolf. Date 08.05.2019.
 */
class DataModule : Module() {
    init {

        bind(PrimitiveWrapper::class.java).withName(BatchSizeNewWord::class.java).toInstance(PrimitiveWrapper(10))
        bind(PrimitiveWrapper::class.java).withName(BatchSizeMemorizedWord::class.java).toInstance(PrimitiveWrapper(70))

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
        bind(RxWordRepository::class.java).to(RxWordRepositoryImpl::class.java)
        bind(WordInteractor::class.java).to(WordInteractorImpl::class.java)
        bind(RxWordInteractor::class.java).to(RxWordInteractorImpl::class.java)
        bind(WordAssetRepository::class.java).to(WordAssetRepositoryImpl::class.java)

        //Phrases
        bind(PhraseRepository::class.java).to(PhraseRepositoryImpl::class.java)
        bind(PhraseTopicRepository::class.java).to(PhraseTopicRepositoryImpl::class.java)
        bind(PhraseTopicInteractor::class.java).to(PhraseTopicInteractorImpl::class.java)

        //Learn
        bind(LearnPhraseRepository::class.java).to(LearnPhraseRepositoryImpl::class.java)
        bind(LearnPhraseInteractor::class.java).to(LearnPhraseInteractorImpl::class.java)

        bind(LearnWordsInteractor::class.java).to(LearnWordsInteractorImpl::class.java)
        bind(LearnWordRepository::class.java).to(LearnWordRepositoryImpl::class.java)
    }
}