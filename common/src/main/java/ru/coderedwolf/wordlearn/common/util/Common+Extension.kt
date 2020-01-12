package ru.coderedwolf.wordlearn.common.util

/**
 * @author CodeRedWolf. Date 29.10.2019.
 */
fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)