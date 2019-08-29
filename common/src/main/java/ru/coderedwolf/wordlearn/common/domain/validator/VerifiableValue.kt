package ru.coderedwolf.wordlearn.common.domain.validator

/**
 * @author CodeRedWolf. Date 23.08.2019.
 */
data class VerifiableValue<V>(
        val value: V,
        val verifiable: Verifiable
) {

    val isValid = verifiable.isValid

}