package ru.haroncode.wordlearn

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.haroncode.wordlearn.common.di.ComponentDependenciesProvider
import ru.haroncode.wordlearn.common.di.ComponentManager
import ru.haroncode.wordlearn.common.di.ComponentManager.inject
import ru.haroncode.wordlearn.common.di.HasChildDependencies
import ru.haroncode.wordlearn.di.DaggerBuildersComponent
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasChildDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initThreeTenAbp()
        initComponentManager()
        inject(javaClass.name)
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initThreeTenAbp() {
        AndroidThreeTen.init(this)
    }

    private fun initComponentManager() {
        val builders = DaggerBuildersComponent.create().builders()
        ComponentManager.initBuilders(builders)
    }
}