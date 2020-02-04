package ru.haroncode.wordlearn.mainflow.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.haroncode.wordlearn.common.di.ViewModelFactoryModule
import ru.haroncode.wordlearn.common.di.ViewModelKey
import ru.haroncode.wordlearn.mainflow.presentation.set.WordSetUserViewModel

/**
 * @author HaronCode. Date 10.11.2019.
 */
@Module
interface ViewModelModule : ViewModelFactoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(WordSetUserViewModel::class)
    fun wordSetViewModel(
        wordSetUserViewModel: WordSetUserViewModel
    ): ViewModel
}
