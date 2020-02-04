package ru.haroncode.wordlearn.common.domain.validator

/**
 * @author HaronCode.
 */
data class VerifiableValue<V>(
    val value: V,
    val verifiable: Verifiable
) {

    val isValid = verifiable.isValid
}
