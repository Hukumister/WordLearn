package ru.coderedwolf.wordlearn

import android.app.Application
import ru.coderedwolf.wordlearn.di.AppComponent
import ru.coderedwolf.wordlearn.di.common.ComponentDependenciesProvider
import ru.coderedwolf.wordlearn.di.common.HasChildDependencies
import timber.log.Timber
import javax.inject.Inject


class App : Application(), HasChildDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initLogger()
        appComponent = buildComponent().apply { inject(this@App) }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .context(this)
            .build()
    }
}