package ru.coderedwolf.mvi.binder

import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import org.reactivestreams.Publisher

private typealias Transformer<In, Out> = (Flowable<In>) -> Flowable<Out>

open class Connection<Out : Any, In : Any>(
    val consumer: Consumer<In>,
    val publisher: Publisher<Out>,
    val transformer: Transformer<Out, In>
)

@Suppress("NOTHING_TO_INLINE")
inline infix fun <Out : Any, In : Any> Pair<Publisher<Out>, Consumer<In>>.with(
    noinline transformer: Transformer<Out, In>
) = Connection(
    consumer = this.second,
    publisher = this.first,
    transformer = transformer
)

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any> noneTransformer(): Transformer<T, T> = { input -> input }