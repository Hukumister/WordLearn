package ru.coderedwolf.wordlearn.mainflow.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.mainflow.presentation.WordsCategoryReachableFlows
import ru.coderedwolf.wordlearn.mainflow.ui.WordsCategoryFragment
import ru.coderedwolf.wordlearn.wordscategory.domain.WordsCategoryInteractor

@Module
object WordsCategoryComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(WordsCategoryFragment::class)
    fun provideWordsCategoryComponentBuilder() = InjectorBuilder<WordsCategoryFragment> {
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
interface WordsCategoryComponent : Injector<WordsCategoryFragment>

interface WordsCategoryDependencies : ScreenDependencies {
    fun wordsCategoryInteractor(): WordsCategoryInteractor
    fun wordsCategoryReachableFlows(): WordsCategoryReachableFlows
}