package ru.coderedwolf.wordlearn.common.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import ru.coderedwolf.wordlearn.common.presentation.ViewModelFactory

/**
 * @author CodeRedWolf. Date 04.11.2019.
 */
@Module
interface ViewModelFactoryModule {

    @Binds
    fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

}