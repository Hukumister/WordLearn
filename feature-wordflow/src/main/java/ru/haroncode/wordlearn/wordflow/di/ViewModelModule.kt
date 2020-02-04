package ru.haroncode.wordlearn.wordflow.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.haroncode.wordlearn.common.di.ViewModelFactoryModule
import ru.haroncode.wordlearn.common.di.ViewModelKey
import ru.haroncode.wordlearn.wordflow.presentation.CreateWordViewModelStore

@Module
interface ViewModelModule : ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(CreateWordViewModelStore::class)
    fun wordSetViewModel(
        createWordViewModelStore: CreateWordViewModelStore
    ): ViewModel
}
