package ru.coderedwolf.wordlearn.di.common

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
interface DaggerComponent

val EMPTY_COMPONENT: DaggerComponent = object :
    DaggerComponent {}