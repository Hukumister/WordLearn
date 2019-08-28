package ru.coderedwolf.wordlearn.common.di

import dagger.Module
import dagger.Provides
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
object FlowNavigationModule {
    @Provides
    @JvmStatic
    @PerFlow
    fun provideCicerone(router: Router) = Cicerone.create(FlowRouter(router))

    @Provides
    @JvmStatic
    @PerFlow
    fun provideFlowRouter(cicerone: Cicerone<FlowRouter>) = cicerone.router

    @Provides
    @JvmStatic
    @PerFlow
    fun provideFlowNavigatorHolder(cicerone: Cicerone<FlowRouter>) = cicerone.navigatorHolder
}