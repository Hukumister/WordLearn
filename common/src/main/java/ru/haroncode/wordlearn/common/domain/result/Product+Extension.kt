package ru.haroncode.wordlearn.common.domain.result

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.annotations.SchedulerSupport
import ru.haroncode.wordlearn.common.util.asSingle

/**
 * @author HaronCode. Date 24.08.2019.
 */
sealed class Product<out T> {

    object Loading : Product<Nothing>()

    data class Data<T>(val value: T) : Product<T>()

    data class Error(val throwable: Throwable) : Product<Nothing>()
}

inline fun <T, R> Product<T>.map(crossinline mapper: (T) -> R): Product<R> = when (this) {
    is Product.Loading -> this
    is Product.Data -> try {
        Product.Data(mapper(value))
    } catch (throwable: Throwable) {
        Product.Error(throwable)
    }
    is Product.Error -> this
}

/**
 * Single<T> -> Observable<Product<T>>
 * Wrap T to Product<T>
 */
@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T> Single<T>.asProduct(): Observable<Product<T>> = map<Product<T>> { data -> Product.Data(data) }
    .onErrorReturn { throwable -> Product.Error(throwable) }
    .toObservable()
    .startWith(Product.Loading)

/**
 * Flowable<T> -> Observable<Product<T>>
 * Wrap T to Product<T>
 */
@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T> Flowable<T>.asProduct(): Flowable<Product<T>> = map<Product<T>> { data -> Product.Data(data) }
    .onErrorReturn { throwable -> Product.Error(throwable) }
    .startWith(Product.Loading)

/**
 * Observable<Product<T>> -> Observable<Product<R>>
 * Map value of income product T to value R of result Product<R>
 */
@CheckReturnValue
@SchedulerSupport(SchedulerSupport.NONE)
fun <T, R> Observable<Product<T>>.mapProduct(mapper: (T) -> R): Observable<Product<R>> = switchMapSingle { product ->
    product.asSingle().map { mapProduct -> mapProduct.map(mapper) }
}.distinctUntilChanged()
