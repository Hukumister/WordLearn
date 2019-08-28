package ru.coderedwolf.wordlearn.common.domain.system

import kotlin.coroutines.CoroutineContext

interface DispatchersProvider {
    fun ui(): CoroutineContext
    fun io(): CoroutineContext
    fun computation(): CoroutineContext
}