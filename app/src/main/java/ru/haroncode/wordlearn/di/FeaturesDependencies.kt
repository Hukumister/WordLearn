package ru.haroncode.wordlearn.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.haroncode.wordlearn.common.di.ComponentDependencies
import ru.haroncode.wordlearn.common.di.ComponentDependenciesKey
import ru.haroncode.wordlearn.mainflow.di.MainFlowDependencies
import ru.haroncode.wordlearn.wordflow.di.WordFlowDependencies

interface FeaturesDependencies :
    AppActivityDependencies,
    MainFlowDependencies,
    WordFlowDependencies,
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
    @ComponentDependenciesKey(StubDependencies::class)
    fun provideStubDependencies(appComponent: AppComponent): ComponentDependencies
}