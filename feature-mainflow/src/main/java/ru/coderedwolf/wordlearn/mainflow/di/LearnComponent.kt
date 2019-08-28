package ru.coderedwolf.wordlearn.mainflow.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.mainflow.presentation.LearnReachableFlows
import ru.coderedwolf.wordlearn.mainflow.ui.LearnFragment

@Module
object LearnComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(LearnFragment::class)
    fun providerLearnComponentBuilder() = InjectorBuilder<LearnFragment> {
        DaggerLearnComponent.builder()
            .learnDependencies(findComponentDependencies())
            .build()
    }
}

@PerFragment
@Component(
    dependencies = [
        LearnDependencies::class
    ]
)
interface LearnComponent : Injector<LearnFragment>

interface LearnDependencies : ScreenDependencies {
    fun learnReachableFlows(): LearnReachableFlows
}