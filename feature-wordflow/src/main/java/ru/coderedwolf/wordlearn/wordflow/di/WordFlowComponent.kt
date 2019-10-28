package ru.coderedwolf.wordlearn.wordflow.di

import dagger.*
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.*
import ru.coderedwolf.wordlearn.common.domain.system.ResourceProvider
import ru.coderedwolf.wordlearn.common.presentation.ErrorHandler
import ru.coderedwolf.wordlearn.database.dao.WordDao
import ru.coderedwolf.wordlearn.database.mapper.WordMapper
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import ru.coderedwolf.wordlearn.wordflow.domain.repository.WordRepositoryImpl
import ru.coderedwolf.wordlearn.wordflow.ui.WordFlowFragment

@Module(
    includes = [
        WordListComponentBuilderModule::class,
        CreateWordComponentBuilderModule::class
    ]
)
object WordFlowComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(WordFlowFragment::class)
    fun provideWordFlowComponentBuilder() = InjectorBuilder<WordFlowFragment> {
        DaggerWordFlowComponent.builder()
            .categoryId(categoryId)
            .categoryName(categoryName)
            .wordFlowDependencies(findComponentDependencies())
            .build()
    }
}

@PerFlow
@Component(
    modules = [
        CreateWordModule::class,
        FlowNavigationModule::class,
        ChildDependenciesModule::class
    ],
    dependencies = [
        WordFlowDependencies::class
    ]
)
interface WordFlowComponent : Injector<WordFlowFragment>,
    WordListDependencies,
    CreateWordDependencies {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun categoryId(categoryId: Long): Builder

        @BindsInstance
        fun categoryName(categoryName: String): Builder

        fun wordFlowDependencies(wordFlowDependencies: WordFlowDependencies): Builder

        fun build(): WordFlowComponent
    }
}

interface WordFlowDependencies : FlowDependencies {
    fun resourceProvider(): ResourceProvider
    fun wordDao(): WordDao
    fun wordMapper(): WordMapper
    fun errorHandler(): ErrorHandler
}

@Module
interface CreateWordModule {
    @Binds
    @PerFlow
    fun provideWordRepository(
        wordRepositoryImpl: WordRepositoryImpl
    ): WordRepository
}

@Module
interface ChildDependenciesModule {
    @Binds
    @IntoMap
    @ComponentDependenciesKey(WordListDependencies::class)
    fun provideWordListDependencies(
        wordFlowComponent: WordFlowComponent
    ): ComponentDependencies

    @Binds
    @IntoMap
    @ComponentDependenciesKey(CreateWordDependencies::class)
    fun provideCreateWordDependencies(
        wordFlowComponent: WordFlowComponent
    ): ComponentDependencies
}