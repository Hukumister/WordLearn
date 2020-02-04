package ru.coderedwolf.wordlearn.common.di

interface Injector<T> {

    fun inject(target: T)
}