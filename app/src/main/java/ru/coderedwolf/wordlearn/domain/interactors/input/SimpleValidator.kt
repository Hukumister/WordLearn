package ru.coderedwolf.wordlearn.domain.interactors.input

import ru.coderedwolf.wordlearn.R


object SimpleValidator {

    fun isNotNullOrEmptyOrBlank(value: CharSequence?) = if (value?.trim().isNullOrEmpty()) {
        ResourceViolation(false, R.string.error_empty)
    } else {
        Validated
    }

}
