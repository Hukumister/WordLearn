package ru.haroncode.wordlearn.common.util

import androidx.collection.SparseArrayCompat

/**
 * @author HaronCode.
 */
fun <T> SparseArrayCompat<T>.putAll(map: Map<Int, T>) = map.forEach { (key, value) -> put(key, value) }
