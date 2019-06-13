package ru.coderedwolf.wordlearn.exception

import ru.coderedwolf.wordlearn.domain.interactors.validator.Violation


class ViolationException(val violation: Violation) : RuntimeException()