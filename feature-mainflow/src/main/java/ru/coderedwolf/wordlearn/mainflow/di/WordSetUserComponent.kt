package ru.coderedwolf.wordlearn.mainflow.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.api.wordset.domain.repository.WordSetRepository
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.mainflow.domain.repository.WordSetRepositoryImpl
import ru.coderedwolf.wordlearn.mainflow.presentation.WordsCategoryReachableFlows
import ru.coderedwolf.wordlearn.mainflow.ui.word.set.WordSetUserFragment

@Module
object WordsCategoryComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(WordSetUserFragment::class)
    fun provideWordsCategoryComponentBuilder() = InjectorBuilder<WordSetUserFragment> {
        DaggerWordSetUserComponent.builder()
            .wordSetUserDependencies(findComponentDependencies())
            .build()
    }
}

@PerFragment
@Component(
    dependencies = [
        WordSetUserDependencies::class
    ]
)
interface WordSetUserComponent : Injector<WordSetUserFragment>

interface WordSetUserDependencies : ScreenDependencies, ViewModelDependencies {
    fun wordsCategoryReachableFlows(): WordsCategoryReachableFlows
}

@Module
interface WordSetModule {

    @Binds
    fun wordSetRepository(
        wordSetRepositoryImpl: WordSetRepositoryImpl
    ): WordSetRepository

}