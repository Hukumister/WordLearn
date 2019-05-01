package ru.coderedwolf.wordlearn.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.coderedwolf.wordlearn.App
import ru.coderedwolf.wordlearn.ui.AppActivity
import javax.inject.Singleton

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(appActivity: AppActivity)
}