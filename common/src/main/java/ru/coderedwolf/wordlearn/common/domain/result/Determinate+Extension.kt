package ru.coderedwolf.wordlearn.common.domain.result

import androidx.annotation.IntDef
import androidx.annotation.IntRange
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport

/**
 * @author CodeRedWolf. Date 24.08.2019.
 */
sealed class Determinate {

    @State
    abstract val state: Int

    @IntRange(from = 0L)
    open val timestamp: Long = System.currentTimeMillis()

    companion object {

        const val STATE_LOADING = 1
        const val STATE_COMPLETED = 1 shl 1
        const val STATE_ERROR = 1 shl 2

        @IntDef(
            STATE_LOADING,
            STATE_COMPLETED,
            STATE_ERROR
        )
        annotation class State

    }

    object Loading : Determinate() {

        override val state: Int = STATE_LOADING

    }

    object Completed : Determinate() {

        override val state: Int = STATE_COMPLETED

    }

    data class Error(
        val throwable: Throwable,
        override val timestamp: Long = System.currentTimeMillis()
    ) : Determinate() {

        override val state: Int = STATE_ERROR

    }

}

/**
 * Wrap Completable to Observable<Determinate>
 * Completable -> Observable<Determinate>
 */
@CheckReturnValue
@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@SchedulerSupport(SchedulerSupport.NONE)
fun Completable.asDeterminate(): Flowable<Determinate> = andThen(
    Flowable.just<Determinate>(
        Determinate.Completed
    )
)
    .startWith(Determinate.Loading)
    .onErrorReturn { throwable -> Determinate.Error(throwable) }