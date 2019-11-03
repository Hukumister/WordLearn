package ru.coderedwolf.wordlearn.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.coderedwolf.wordlearn.common.presentation.ViewModelFactory
import javax.inject.Singleton

/**
 * @author CodeRedWolf. Date 04.11.2019.
 */
@Module
interface ViewModelModule {

    @Binds
    @Singleton
    fun provideViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory

}