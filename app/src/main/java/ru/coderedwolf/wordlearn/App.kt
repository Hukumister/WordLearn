package ru.coderedwolf.wordlearn

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.coderedwolf.wordlearn.di.DI
import ru.coderedwolf.wordlearn.di.module.AppModule
import ru.coderedwolf.wordlearn.di.module.DataModule
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
        initToothpick()
        initThreeTenAbp()
        initAppScope()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initToothpick() {
        if (BuildConfig.DEBUG) {
            Toothpick.setConfiguration(Configuration.forDevelopment().preventMultipleRootScopes())
        } else {
            Toothpick.setConfiguration(Configuration.forProduction().disableReflection())
        }
    }

    private fun initAppScope() {
        Toothpick.openScope(DI.APP_SCOPE).installModules(AppModule(this), DataModule())
    }

    private fun initThreeTenAbp() {
        AndroidThreeTen.init(this)
    }
}