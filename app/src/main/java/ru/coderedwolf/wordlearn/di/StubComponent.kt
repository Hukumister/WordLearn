package ru.coderedwolf.wordlearn.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.ui.StubFragment

@Module
object StubComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(StubFragment::class)
    fun provideStubComponentBuilder() = InjectorBuilder<StubFragment> {
        DaggerStubComponent.builder()
            .stubDependencies(findComponentDependencies())
            .build()
    }
}

@PerFlow
@Component(
    dependencies = [
        StubDependencies::class
    ]
)
interface StubComponent : Injector<StubFragment>

interface StubDependencies : FlowDependencies