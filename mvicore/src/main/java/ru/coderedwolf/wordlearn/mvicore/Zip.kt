package ru.coderedwolf.wordlearn.mvicore

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

@ExperimentalCoroutinesApi
@Suppress("NOTHING_TO_INLINE")
inline fun <T1, T2> Flow<T1>.combine(flow: Flow<T2>) = combine(flow) { a, b -> a to b }