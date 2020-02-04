package ru.haroncode.wordlearn.di

import dagger.Component
import ru.haroncode.wordlearn.common.di.BuildersProvider
import ru.haroncode.wordlearn.mainflow.di.MainFlowComponentBuilderModule
import ru.haroncode.wordlearn.wordflow.di.WordFlowComponentBuilderModule
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