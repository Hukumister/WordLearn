package ru.haroncode.wordlearn.di

import android.content.Context
import dagger.*
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import ru.haroncode.wordlearn.App
import ru.haroncode.wordlearn.common.di.Injector
import ru.haroncode.wordlearn.common.di.InjectorBuilder
import ru.haroncode.wordlearn.common.domain.system.SchedulerProvider
import ru.haroncode.wordlearn.domain.system.AppSchedulerProvider

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
