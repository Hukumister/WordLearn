package ru.coderedwolf.learnword.learnphrasesflow.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.learnword.learnphrasesflow.domain.interactor.LearnPhraseInteractor
import ru.coderedwolf.learnword.learnphrasesflow.domain.interactor.LearnPhraseInteractorMock
import ru.coderedwolf.learnword.learnphrasesflow.domain.repository.LearnPhraseRepository
import ru.coderedwolf.learnword.learnphrasesflow.domain.repository.MockLearnPhraseRepository
import ru.coderedwolf.learnword.learnphrasesflow.domain.repository.PhraseRepository
import ru.coderedwolf.learnword.learnphrasesflow.domain.repository.PhraseRepositoryImpl
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.common.domain.system.TimeProvider
import ru.coderedwolf.wordlearn.database.dao.PhraseDao
import ru.coderedwolf.wordlearn.database.dao.PhraseTopicDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseMapper
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper

@Module
object LearnPhrasesFlowComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(LearnPhrasesFlowFragment::class)
    fun provideLearnPhrasesFlowComponentBuilder() = InjectorBuilder<LearnPhrasesFlowFragment> {
        DaggerLearnPhrasesFlowComponent.builder()
                .learnPhrasesFlowDependencies(findComponentDependencies())
                .build()
    }
}

@PerFlow
@Component(
        modules = [
            LearnPhrasesFlowModule::class
        ],
        dependencies = [
            LearnPhrasesFlowDependencies::class
        ]
)
interface LearnPhrasesFlowComponent : Injector<LearnPhrasesFlowFragment>

interface LearnPhrasesFlowDependencies : FlowDependencies {
    fun phraseDao(): PhraseDao
    fun phraseTopicDao(): PhraseTopicDao
    fun phraseMapper(): PhraseMapper
    fun phraseTopicMapper(): PhraseTopicMapper
    fun timeProvider(): TimeProvider
}

@Module
interface LearnPhrasesFlowModule {
    @Binds
    @PerFlow
    fun provideLearnPhraseRepository(
            learnPhraseRepositoryImpl: MockLearnPhraseRepository
    ): LearnPhraseRepository

    @Binds
    @PerFlow
    fun providePhraseRepository(
            phraseRepositoryImpl: PhraseRepositoryImpl
    ): PhraseRepository

    @Binds
    @PerFlow
    fun provideLearnPhraseInteractor(
            learnPhraseInteractorImpl: LearnPhraseInteractorMock
    ): LearnPhraseInteractor
}