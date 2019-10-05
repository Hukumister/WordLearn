package ru.coderedwolf.wordlearn.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.coderedwolf.wordlearn.mainflow.presentation.MainFlowReachableFlows
import ru.coderedwolf.wordlearn.presentation.ReachableFlows
import ru.coderedwolf.wordlearn.ui.Flows
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

@Module
abstract class NavigationModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideCicerone() = Cicerone.create()

        @Provides
        @JvmStatic
        @Singleton
        fun provideRouter(cicerone: Cicerone<Router>) = cicerone.router

        @Provides
        @JvmStatic
        @Singleton
        fun provideNavigatorHolder(cicerone: Cicerone<Router>) = cicerone.navigatorHolder

        @Provides
        @JvmStatic
        @Singleton
        fun provideReachableFlows() = ReachableFlowsNavigator() as ReachableFlows
    }

    @Binds
    abstract fun provideMainFlowReachableFlows(
        reachableFlows: ReachableFlows
    ): MainFlowReachableFlows
}

class ReachableFlowsNavigator : ReachableFlows {

    override fun learnWordsFlow() = Flows.Stub

    override fun learnPhrasesFlow() = Flows.Stub

    override fun phraseFlow(id: Long) = Flows.Stub

    override fun wordFlow(
        categoryId: Long,
        categoryName: String
    ) = Flows.Stub
}