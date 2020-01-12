package ru.coderedwolf.wordlearn.di

import dagger.Component
import ru.coderedwolf.wordlearn.common.di.BuildersProvider
import ru.coderedwolf.wordlearn.mainflow.di.MainFlowComponentBuilderModule
import ru.coderedwolf.wordlearn.wordflow.di.WordFlowComponentBuilderModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppComponentBuilderModule::class,
        AppActivityComponentBuilderModule::class,
        MainFlowComponentBuilderModule::class,
        WordFlowComponentBuilderModule::class,
        StubComponentBuilderModule::class
    ]
)
interface BuildersComponent {
    fun builders(): BuildersProvider
}