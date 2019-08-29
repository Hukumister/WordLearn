package ru.coderedwolf.wordlearn.common.domain.validator

import androidx.annotation.StringRes

/**
 * @author CodeRedWolf. Date 23.08.2019.
 */
sealed class Verifiable {

    abstract val isValid: Boolean

}

object Validated : Verifiable() {

    override val isValid: Boolean = true

}

object NotValid : Verifiable() {

    override val isValid: Boolean = false

}

data class ResourceViolation(
        override val isValid: Boolean,
        @StringRes val res: Int
) : Verifiable()

