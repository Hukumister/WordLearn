package ru.coderedwolf.wordlearn.di

import android.content.Context
import dagger.*
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.App
import ru.coderedwolf.wordlearn.common.di.Injector
import ru.coderedwolf.wordlearn.common.di.InjectorBuilder
import ru.coderedwolf.wordlearn.common.domain.system.SchedulerProvider
import ru.coderedwolf.wordlearn.domain.system.AppSchedulerProvider
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
        fun provideAssetManager(context: Context) = context.assets
    }

    @Binds
    @Singleton
    abstract fun provideSchedulerProvider(
        schedulerProvider: AppSchedulerProvider
    ): SchedulerProvider
}