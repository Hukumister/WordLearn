package ru.coderedwolf.wordlearn.common.util

/**
 * @author HaronCode.
 */
fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)