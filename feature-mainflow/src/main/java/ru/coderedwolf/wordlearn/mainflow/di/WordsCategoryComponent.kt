package ru.coderedwolf.wordlearn.mainflow.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.mainflow.presentation.WordsCategoryReachableFlows
import ru.coderedwolf.wordlearn.mainflow.ui.word.set.WordSetFragment

@Module
object WordsCategoryComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(WordSetFragment::class)
    fun provideWordsCategoryComponentBuilder() = InjectorBuilder<WordSetFragment> {
        DaggerWordsCategoryComponent.builder()
            .wordsCategoryDependencies(findComponentDependencies())
            .build()
    }
}

@PerFragment
@Component(
    dependencies = [
        WordsCategoryDependencies::class
    ]
)
interface WordsCategoryComponent : Injector<WordSetFragment>

interface WordsCategoryDependencies : ScreenDependencies {
    fun wordsCategoryReachableFlows(): WordsCategoryReachableFlows
}