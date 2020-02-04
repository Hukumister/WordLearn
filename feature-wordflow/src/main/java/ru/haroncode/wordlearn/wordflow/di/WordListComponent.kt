package ru.haroncode.wordlearn.wordflow.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.haroncode.wordlearn.common.di.*
import ru.haroncode.wordlearn.wordflow.ui.WordListFragment

@Module
object WordListComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(WordListFragment::class)
    fun provideWordListComponentBuilder() = InjectorBuilder<WordListFragment> {
        DaggerWordListComponent.builder()
            .wordListDependencies(findComponentDependencies())
            .build()
    }
}

@PerFragment
@Component(
    dependencies = [
        WordListDependencies::class
    ]
)
interface WordListComponent : Injector<WordListFragment>

interface WordListDependencies : ScreenDependencies {
    fun categoryId(): Long
    fun categoryName(): String
}
