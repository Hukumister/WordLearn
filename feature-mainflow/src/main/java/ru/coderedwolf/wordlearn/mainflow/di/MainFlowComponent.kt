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
import ru.coderedwolf.wordlearn.mainflow.presentation.LearnReachableFlows
import ru.coderedwolf.wordlearn.mainflow.presentation.MainFlowReachableFlows
import ru.coderedwolf.wordlearn.mainflow.presentation.PhraseTopicReachableFlows
import ru.coderedwolf.wordlearn.mainflow.presentation.WordsCategoryReachableFlows
import ru.coderedwolf.wordlearn.mainflow.ui.MainFlowFragment
import ru.coderedwolf.wordlearn.wordscategory.domain.WordsCategoryInteractor

@Module(
    includes = [
        LearnComponentBuilderModule::class,
        PhraseTopicComponentBuilderModule::class,
        WordsCategoryComponentBuilderModule::class
    ]
)
object MainFlowComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(MainFlowFragment::class)
    fun provideMainFlowComponentBuilder() = InjectorBuilder<MainFlowFragment> {
        DaggerMainFlowComponent.builder()
            .mainFlowDependencies(findComponentDependencies())
            .build()
    }
}

@PerFlow
@Component(
    modules = [
        FlowNavigationModule::class,
        ChildDependenciesModule::class
    ],
    dependencies = [
        MainFlowDependencies::class
    ]
)
interface MainFlowComponent : Injector<MainFlowFragment>,
    LearnDependencies,
    PhraseTopicDependencies,
    WordsCategoryDependencies

@Module
interface ChildDependenciesModule {
    @Binds
    fun provideLearnReachableScreens(
        mainFlowReachableScreens: MainFlowReachableFlows
    ): LearnReachableFlows

    @Binds
    fun providePhraseTopicReachableScreens(
        mainFlowReachableScreens: MainFlowReachableFlows
    ): PhraseTopicReachableFlows

    @Binds
    fun provideWordsCategoryReachableScreens(
        mainFlowReachableScreens: MainFlowReachableFlows
    ): WordsCategoryReachableFlows

    @Binds
    @IntoMap
    @ComponentDependenciesKey(LearnDependencies::class)
    fun provideLearnDependencies(mainFlowComponent: MainFlowComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(PhraseTopicDependencies::class)
    fun providePhraseTopicDependencies(mainFlowComponent: MainFlowComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(WordsCategoryDependencies::class)
    fun provideWordsCategoryDependencies(mainFlowComponent: MainFlowComponent): ComponentDependencies
}

interface MainFlowDependencies : FlowDependencies {
    fun phraseTopicDao(): PhraseTopicDao
    fun phraseTopicMapper(): PhraseTopicMapper
    fun wordsCategoryInteractor(): WordsCategoryInteractor
    fun mainFlowReachableScreens(): MainFlowReachableFlows
}