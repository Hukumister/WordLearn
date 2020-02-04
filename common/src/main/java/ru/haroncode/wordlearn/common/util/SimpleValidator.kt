package ru.haroncode.wordlearn.common.util

import ru.haroncode.wordlearn.common.R
import ru.haroncode.wordlearn.common.domain.validator.ResourceViolation
import ru.haroncode.wordlearn.common.domain.validator.Validated

object SimpleValidator {

    fun isNotNullOrEmptyOrBlank(value: CharSequence?) = if (value?.trim().isNullOrEmpty()) {
        ResourceViolation(false, R.string.error_empty)
    } else {
        Validated
    }
}
