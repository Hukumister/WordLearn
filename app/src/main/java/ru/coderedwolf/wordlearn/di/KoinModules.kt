package ru.coderedwolf.wordlearn.di

import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone


val appModule = module {

}

val routingModule = module {
    val cicerone = Cicerone.create()

    single { cicerone.router!! }
    single { cicerone.navigatorHolder!! }
}


val presenterModule = module {


}