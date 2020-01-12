package ru.coderedwolf.wordlearn.wordflow.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.ViewModelFactoryModule
import ru.coderedwolf.wordlearn.common.di.ViewModelKey
import ru.coderedwolf.wordlearn.wordflow.presentation.CreateWordViewModelStore

@Module
interface ViewModelModule : ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateWordViewModelStore::class)
    fun wordSetViewModel(
        createWordViewModelStore: CreateWordViewModelStore
    ): ViewModel

}