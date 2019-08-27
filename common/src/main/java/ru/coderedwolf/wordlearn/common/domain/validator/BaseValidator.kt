package ru.coderedwolf.wordlearn.common.domain.validator

import androidx.annotation.StringRes
import ru.coderedwolf.wordlearn.common.R
import ru.coderedwolf.wordlearn.common.domain.system.ResourceProvider

/**
 * @author CodeRedWolf. Date 09.06.2019.
 */
abstract class BaseValidator<T>(private val resourceProvider: ResourceProvider) {

    abstract fun validate(original: T): Violation

    protected fun validForEmpty(
        string: String,
        @StringRes errorMessage: Int = R.string.error_empty
    ): String = when {
        string.isEmpty() -> resourceProvider.getString(errorMessage)
        else -> ""
    }
}