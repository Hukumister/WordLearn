package ru.coderedwolf.mvi.binder.dsl

import io.reactivex.functions.Consumer
import org.reactivestreams.Publisher
import ru.coderedwolf.mvi.connection.BaseConnectionRule
import ru.coderedwolf.mvi.connection.Transformer

@Suppress("NOTHING_TO_INLINE")
inline infix fun <Out : Any, In : Any> Pair<Publisher<Out>, Consumer<In>>.with(
    noinline transformer: Transformer<Out, In>
) = BaseConnectionRule(
    consumer = this.second,
    publisher = this.first,
    transformer = transformer
)

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any> noneTransformer(): Transformer<T, T> = { input -> input }