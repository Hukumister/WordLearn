package ru.haroncode.wordlearn.common.util

/**
 * @author HaronCode.
 */
fun <T> unsafeLazy(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)