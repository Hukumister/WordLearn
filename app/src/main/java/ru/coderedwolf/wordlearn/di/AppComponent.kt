package ru.coderedwolf.wordlearn.di

import android.content.Context
import dagger.*
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.App
import ru.coderedwolf.wordlearn.common.di.Injector
import ru.coderedwolf.wordlearn.common.di.InjectorBuilder
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.domain.system.AppDispatchersProvider
import ru.coderedwolf.wordlearn.domain.system.AppSchedulerProvider
import ru.coderedwolf.wordlearn.domain.system.SystemTimeProvider
import javax.inject.Singleton

@Module
object AppComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(App::class)
    fun provideAppComponentBuilder() = InjectorBuilder<App> {
        DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}

@Singleton
@Component(
    modules = [
        AppModule::class,
        NavigationModule::class,
        DatabaseModule::class,
        WordsModule::class,
        PrepopulateModule::class,
        ChildComponentsDependenciesModule::class
    ]
)
interface AppComponent : Injector<App>, FeaturesDependencies {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}

@Module
abstract class AppModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideResourceProvider(context: Context) = ResourceProvider(context)

        @Provides
        @JvmStatic
        @Singleton
        fun provideAssetManager(context: Context) = context.assets

        @Provides
        @JvmStatic
        @Singleton
        fun provideErrorHandler(resourceProvider: ResourceProvider) = ErrorHandler(resourceProvider)
    }

    @Binds
    @Singleton
    abstract fun provideDispatchersProvider(
        appDispatchersProvider: AppDispatchersProvider
    ): DispatchersProvider

    @Binds
    @Singleton
    abstract fun provideSchedulerProvider(
        schedulerProvider: AppSchedulerProvider
    ): SchedulerProvider

    @Binds
    @Singleton
    abstract fun provideTimeProvider(
        systemTimeProvider: SystemTimeProvider
    ): TimeProvider
}