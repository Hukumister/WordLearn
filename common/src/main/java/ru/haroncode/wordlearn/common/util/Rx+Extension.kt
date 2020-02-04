@file:Suppress("NOTHING_TO_INLINE")

package ru.haroncode.wordlearn.common.util

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

/**
 * @author HaronCode.
 */
inline fun <T> T.asObservable() = Observable.just(this)

inline fun <T> T.asFlowable() = Flowable.just(this)

inline fun <T> T.asSingle() = Single.just(this)
