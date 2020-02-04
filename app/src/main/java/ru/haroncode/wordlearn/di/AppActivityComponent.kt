package ru.haroncode.wordlearn.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Singleton
import ru.haroncode.wordlearn.common.di.ComponentDependencies
import ru.haroncode.wordlearn.common.di.Injector
import ru.haroncode.wordlearn.common.di.InjectorBuilder
import ru.haroncode.wordlearn.common.di.findComponentDependencies
import ru.haroncode.wordlearn.ui.AppActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
object AppActivityComponentBuilderModule {
    @Provides
    @JvmStatic
    @IntoMap
    @ClassKey(AppActivity::class)
    fun provideAppActivityComponentBuilder() = InjectorBuilder<AppActivity> {
        DaggerAppActivityComponent.builder()
            .appActivityDependencies(findComponentDependencies())
            .build()
    }
}

@Singleton
@Component(
    dependencies = [
        AppActivityDependencies::class
    ]
)
interface AppActivityComponent : Injector<AppActivity>

interface AppActivityDependencies : ComponentDependencies {
    fun navigatorHolder(): NavigatorHolder
    fun router(): Router
}
