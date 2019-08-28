package ru.coderedwolf.wordlearn.common.domain.validator

class Violation(private val errorMap: Map<String, String>) : Map<String, String> by errorMap {

    companion object {

        fun from(map: Map<String, String>): Violation {
            return Violation(map)
        }

        fun of(vararg pairs: Pair<String, String>): Violation {
            return Violation(mapOf(*pairs))
        }
    }

    val hasError: Boolean
        get() = errorMap.values.any { it.isNotEmpty() }

    val errorCount: Int
        get() = errorMap.values.filter { it.isNotEmpty() }.count()

    fun throwIfHasError() {
        if (hasError) {
            throw ViolationException(this)
        }
    }
}