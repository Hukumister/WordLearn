package ru.coderedwolf.wordlearn.common.extension

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.coderedwolf.wordlearn.common.domain.validator.Verifiable
import ru.coderedwolf.wordlearn.common.domain.validator.VerifiableValue

fun <T> Flow<T>.verify(
    acceptor: (T) -> Verifiable
): Flow<VerifiableValue<T>> = map { value ->
    val acceptable = acceptor(value)
    VerifiableValue(value, acceptable)
}