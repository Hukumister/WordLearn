package ru.coderedwolf.wordlearn.common.domain.result

import androidx.annotation.IntRange
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import ru.coderedwolf.wordlearn.common.util.asObservable

/**
 * @author CodeRedWolf. Date 24.08.2019.
 */
sealed class Determinate {

    @IntRange(from = 0L)
    open val timestamp: Long = System.currentTimeMillis()

    object Loading : Determinate()

    object Completed : Determinate()

    data class Error(
        val throwable: Throwable,
        override val timestamp: Long = System.currentTimeMillis()
    ) : Determinate()

}

/**
 * Wrap Completable to Observable<Determinate>
 * Completable -> Observable<Determinate>
 */
@CheckReturnValue
@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@SchedulerSupport(SchedulerSupport.NONE)
fun Completable.asDeterminate(): Observable<Determinate> = andThen(Determinate.Completed.asObservable<Determinate>())
    .startWith(Determinate.Loading)
    .onErrorReturn { throwable -> Determinate.Error(throwable) }