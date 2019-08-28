package ru.coderedwolf.wordlearn.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.coderedwolf.learnword.learnphrasesflow.di.LearnPhrasesFlowDependencies
import ru.coderedwolf.wordlearn.common.di.ComponentDependencies
import ru.coderedwolf.wordlearn.common.di.ComponentDependenciesKey
import ru.coderedwolf.wordlearn.learnwordsflow.di.LearnWordsFlowDependencies
import ru.coderedwolf.wordlearn.mainflow.di.MainFlowDependencies
import ru.coderedwolf.wordlearn.wordflow.di.WordFlowDependencies

interface FeaturesDependencies :
    AppActivityDependencies,
    MainFlowDependencies,
    WordFlowDependencies,
    LearnWordsFlowDependencies,
    LearnPhrasesFlowDependencies,
    StubDependencies

@Module
interface ChildComponentsDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(AppActivityDependencies::class)
    fun provideAppActivityDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(MainFlowDependencies::class)
    fun provideMainFlowDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(WordFlowDependencies::class)
    fun provideWordFlowDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(LearnWordsFlowDependencies::class)
    fun provideLearnWordsFlowDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(LearnPhrasesFlowDependencies::class)
    fun provideLearnPhrasesFlowDependencies(appComponent: AppComponent): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(StubDependencies::class)
    fun provideStubDependencies(appComponent: AppComponent): ComponentDependencies
}