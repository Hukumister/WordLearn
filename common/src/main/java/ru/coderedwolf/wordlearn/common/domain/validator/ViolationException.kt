package ru.coderedwolf.wordlearn.common.domain.validator

class ViolationException(val violation: Violation) : RuntimeException()