package ru.haroncode.wordlearn.mainflow.di

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.haroncode.api.wordset.domain.repository.WordSetRepository
import ru.haroncode.wordlearn.common.di.*
import ru.haroncode.wordlearn.mainflow.domain.repository.WordSetRepositoryImpl
import ru.haroncode.wordlearn.mainflow.ui.word.set.user.WordSetUserFragment

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

interface WordSetUserDependencies : ScreenDependencies

@Module
interface WordSetModule {

    @Binds
    fun wordSetRepository(
        wordSetRepositoryImpl: WordSetRepositoryImpl
    ): WordSetRepository
}
