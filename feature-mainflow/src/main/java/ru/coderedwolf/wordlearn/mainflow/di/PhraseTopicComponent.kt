package ru.coderedwolf.wordlearn.mainflow.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.database.dao.PhraseTopicDao
import ru.coderedwolf.wordlearn.database.mapper.PhraseTopicMapper
import ru.coderedwolf.wordlearn.mainflow.domain.interactor.PhraseTopicInteractor
import ru.coderedwolf.wordlearn.mainflow.domain.interactor.PhraseTopicInteractorImpl
import ru.coderedwolf.wordlearn.mainflow.domain.repository.PhraseTopicRepository
import ru.coderedwolf.wordlearn.mainflow.domain.repository.PhraseTopicRepositoryImpl
import ru.coderedwolf.wordlearn.mainflow.presentation.PhraseTopicReachableFlows
import ru.coderedwolf.wordlearn.mainflow.ui.PhraseTopicListFragment

@Module
object PhraseTopicComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(PhraseTopicListFragment::class)
    fun providerPhraseTopicComponentBuilder() = InjectorBuilder<PhraseTopicListFragment> {
        DaggerPhraseTopicComponent.builder()
            .phraseTopicDependencies(findComponentDependencies())
            .build()
    }
}

@PerFragment
@Component(
    modules = [
        PhraseTopicModule::class
    ],
    dependencies = [
        PhraseTopicDependencies::class
    ]
)
interface PhraseTopicComponent : Injector<PhraseTopicListFragment>

interface PhraseTopicDependencies : ScreenDependencies {
    fun phraseTopicDao(): PhraseTopicDao
    fun phraseTopicMapper(): PhraseTopicMapper
    fun phraseTopicReachableFlows(): PhraseTopicReachableFlows
}

@Module
interface PhraseTopicModule {
    @Binds
    @PerFragment
    fun providePhraseTopicRepository(
        phraseTopicRepositoryImpl: PhraseTopicRepositoryImpl
    ): PhraseTopicRepository

    @Binds
    @PerFragment
    fun providePhraseTopicInteractor(
        phraseTopicInteractorImpl: PhraseTopicInteractorImpl
    ): PhraseTopicInteractor
}