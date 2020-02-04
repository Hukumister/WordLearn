package ru.haroncode.wordlearn.mainflow.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.haroncode.wordlearn.common.di.*
import ru.haroncode.wordlearn.database.dao.WordSetDao
import ru.haroncode.wordlearn.database.mapper.WordSetMapper
import ru.haroncode.wordlearn.mainflow.presentation.LearnReachableFlows
import ru.haroncode.wordlearn.mainflow.presentation.MainFlowReachableFlows
import ru.haroncode.wordlearn.mainflow.presentation.PhraseTopicReachableFlows
import ru.haroncode.wordlearn.mainflow.presentation.WordsCategoryReachableFlows
import ru.haroncode.wordlearn.mainflow.ui.MainFlowFragment

@Module(
    includes = [
        LearnComponentBuilderModule::class,
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
        ViewModelModule::class,
        FlowNavigationModule::class,
        ChildDependenciesModule::class
    ],
    dependencies = [
        MainFlowDependencies::class
    ]
)
interface MainFlowComponent : Injector<MainFlowFragment>,
    LearnDependencies,
    WordSetUserDependencies

@Module(
    includes = [
        WordSetModule::class
    ]
)
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
    @ComponentDependenciesKey(WordSetUserDependencies::class)
    fun provideWordSetUserComponentDependencies(mainFlowComponent: MainFlowComponent): ComponentDependencies

}

interface MainFlowDependencies : FlowDependencies {

    fun wordSetDao(): WordSetDao
    fun wordSetMapper(): WordSetMapper

    fun mainFlowReachableScreens(): MainFlowReachableFlows
}