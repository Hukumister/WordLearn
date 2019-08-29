package ru.coderedwolf.wordlearn.wordflow.presentation

import ru.coderedwolf.wordlearn.common.domain.validator.ResourceViolation
import ru.coderedwolf.wordlearn.common.domain.validator.Validated
import ru.coderedwolf.wordlearn.wordflow.R


object SimpleValidator {

    fun isNotNullOrEmptyOrBlank(value: CharSequence?) = if (value?.trim().isNullOrEmpty()) {
        ResourceViolation(false, R.string.error_empty)
    } else {
        Validated
    }

}
