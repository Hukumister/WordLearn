package ru.coderedwolf.wordlearn.common

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersProvider {
    fun ui(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
}