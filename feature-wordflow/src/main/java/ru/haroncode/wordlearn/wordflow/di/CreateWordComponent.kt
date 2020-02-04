package ru.haroncode.wordlearn.wordflow.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.haroncode.wordlearn.common.di.*
import ru.haroncode.wordlearn.word.domain.repository.WordRepository
import ru.haroncode.wordlearn.wordflow.ui.CreateWordFragment

@Module
object CreateWordComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(CreateWordFragment::class)
    fun provideCreateWordComponentBuilder() = InjectorBuilder<CreateWordFragment> {
        DaggerCreateWordComponent.builder()
            .createWordDependencies(findComponentDependencies())
            .build()
    }
}

@PerFragment
@Component(
    dependencies = [
        CreateWordDependencies::class
    ]
)
interface CreateWordComponent : Injector<CreateWordFragment>

interface CreateWordDependencies : ScreenDependencies, ViewModelDependencies {
    fun categoryId(): Long
    fun wordRepository(): WordRepository
}