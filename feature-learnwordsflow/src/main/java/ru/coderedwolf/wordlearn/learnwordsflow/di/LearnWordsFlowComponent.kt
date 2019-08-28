package ru.coderedwolf.wordlearn.learnwordsflow.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.database.dao.CategoryAndWordDao
import ru.coderedwolf.wordlearn.database.mapper.CategoryMapper
import ru.coderedwolf.wordlearn.database.mapper.WordMapper
import ru.coderedwolf.wordlearn.learnwordsflow.domain.interactor.LearnWordsInteractor
import ru.coderedwolf.wordlearn.learnwordsflow.domain.interactor.LearnWordsInteractorImpl
import ru.coderedwolf.wordlearn.learnwordsflow.domain.repository.LearnWordRepository
import ru.coderedwolf.wordlearn.learnwordsflow.domain.repository.LearnWordRepositoryImpl
import ru.coderedwolf.wordlearn.learnwordsflow.ui.LearnWordsFlowFragment
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import javax.inject.Qualifier

@Qualifier
annotation class BatchSizeNewWord

@Qualifier
annotation class BatchSizeMemorizedWord

@Module
object LearnWordsFlowComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(LearnWordsFlowFragment::class)
    fun provideLearnWordsFlowComponentBuilder() = InjectorBuilder<LearnWordsFlowFragment> {
        DaggerLearnWordsFlowComponent.builder()
            .learnWordsFlowDependencies(findComponentDependencies())
            .build()
    }
}

@PerFlow
@Component(
    modules = [
        LearnWordsFlowModule::class
    ],
    dependencies = [
        LearnWordsFlowDependencies::class
    ]
)
interface LearnWordsFlowComponent : Injector<LearnWordsFlowFragment>

interface LearnWordsFlowDependencies : FlowDependencies {
    fun categoryAndWordDao(): CategoryAndWordDao
    fun wordMapper(): WordMapper
    fun categoryMapper(): CategoryMapper
    fun wordRepository(): WordRepository
}

@Module
abstract class LearnWordsFlowModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @BatchSizeNewWord
        @PerFlow
        fun provideBatchSizeNewWord() = 10

        @Provides
        @JvmStatic
        @BatchSizeMemorizedWord
        @PerFlow
        fun provideBatchSizeMemorizedWord() = 70
    }

    @Binds
    @PerFlow
    abstract fun provideLearnWordRepository(
        learnWordRepositoryImpl: LearnWordRepositoryImpl
    ): LearnWordRepository

    @Binds
    @PerFlow
    abstract fun provideLearnWordsInteractor(
        learnWordsInteractorImpl: LearnWordsInteractorImpl
    ): LearnWordsInteractor
}
