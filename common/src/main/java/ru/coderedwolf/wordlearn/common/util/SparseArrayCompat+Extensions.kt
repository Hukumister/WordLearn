package ru.coderedwolf.wordlearn.common.util

import androidx.collection.SparseArrayCompat

/**
 * @author CodeRedWolf. Date 20.10.2019.
 */
fun <T> SparseArrayCompat<T>.putAll(map: Map<Int, T>) = map
    .forEach { (key, value) -> put(key, value) }
