package ru.haroncode.wordlearn.common.extension

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.BackpressureKind
import io.reactivex.annotations.BackpressureSupport
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import io.reactivex.flowables.ConnectableFlowable
import io.reactivex.subjects.BehaviorSubject
import ru.haroncode.wordlearn.common.domain.validator.Verifiable
import ru.haroncode.wordlearn.common.domain.validator.VerifiableValue

/**
 * Shares the latest item emitted by that Publisher
 * [Flowable] -> [ConnectableFlowable] like a [BehaviorSubject]
 */
@CheckReturnValue
@BackpressureSupport(BackpressureKind.UNBOUNDED_IN)
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Flowable<T>.toBehaviorFlowable(): Flowable<T> = replay(1).refCount()

/**
 * Returns a Flowable that emits the item in a specified [single] before it begins to emit
 * items emitted by the source Publisher.
 */
fun <T> Flowable<T>.startWith(single: Single<T>): Flowable<T> =
        Flowable.concatArray(single.toFlowable(), this)

@CheckReturnValue
@BackpressureSupport(BackpressureKind.FULL)
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Flowable<T>.verify(
    acceptor: (T) -> Verifiable
): Flowable<VerifiableValue<T>> = map { value ->
    val acceptable = acceptor.invoke(value)
    VerifiableValue(value, acceptable)
}

@CheckReturnValue
@BackpressureSupport(BackpressureKind.FULL)
@SchedulerSupport(SchedulerSupport.NONE)
fun <T : Any> Observable<T>.verify(
    acceptor: (T) -> Verifiable
): Observable<VerifiableValue<T>> = map { value ->
    val acceptable = acceptor.invoke(value)
    VerifiableValue(value, acceptable)
}
