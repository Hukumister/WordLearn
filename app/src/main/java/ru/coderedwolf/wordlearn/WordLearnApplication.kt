package ru.coderedwolf.wordlearn

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.coderedwolf.wordlearn.di.appModule
import ru.coderedwolf.wordlearn.di.presenterModule
import ru.coderedwolf.wordlearn.di.routingModule

@Suppress("unused")
class WordLearnApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WordLearnApplication)
            androidLogger()

            modules(appModule, presenterModule, routingModule)
        }
    }
}