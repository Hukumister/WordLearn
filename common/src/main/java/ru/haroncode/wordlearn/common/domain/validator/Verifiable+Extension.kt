package ru.haroncode.wordlearn.common.domain.validator

import androidx.annotation.StringRes
import ru.haroncode.wordlearn.common.domain.resource.FormattedText
import ru.haroncode.wordlearn.common.domain.resource.toFormatted

/**
 * @author HaronCode.
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
    val formattedText: FormattedText
) : Verifiable() {

    constructor(
        isValid: Boolean,
        @StringRes stringRes: Int,
        vararg args: Any?
    ) : this(isValid, stringRes.toFormatted(args))
}
