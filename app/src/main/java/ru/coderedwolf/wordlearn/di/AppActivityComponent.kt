package ru.coderedwolf.wordlearn.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.coderedwolf.wordlearn.common.di.ComponentDependencies
import ru.coderedwolf.wordlearn.common.di.Injector
import ru.coderedwolf.wordlearn.common.di.InjectorBuilder
import ru.coderedwolf.wordlearn.common.di.findComponentDependencies
import ru.coderedwolf.wordlearn.domain.interactors.PrePopulateDataBaseInteractor
import ru.coderedwolf.wordlearn.ui.AppActivity
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

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
    fun prePopulateDataBaseInteractor(): PrePopulateDataBaseInteractor
    fun navigatorHolder(): NavigatorHolder
    fun router(): Router
}