package ru.coderedwolf.wordlearn.domain.interactors.input

import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.common.domain.validator.ResourceViolation
import ru.coderedwolf.wordlearn.common.domain.validator.Validated


object SimpleValidator {

    fun isNotNullOrEmptyOrBlank(value: CharSequence?) = if (value?.trim().isNullOrEmpty()) {
        ResourceViolation(false, R.string.error_empty)
    } else {
        Validated
    }

}
