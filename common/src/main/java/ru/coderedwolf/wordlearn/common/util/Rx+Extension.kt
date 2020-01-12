@file:Suppress("NOTHING_TO_INLINE")

package ru.coderedwolf.wordlearn.common.util

import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author CodeRedWolf. Date 06.11.2019.
 */
inline fun <T> T.asObservable() = Observable.just(this)

inline fun <T> T.asSingle() = Single.just(this)