package ru.coderedwolf.wordlearn.common.domain.system

import org.threeten.bp.Instant

interface TimeProvider {
    val nowTime: Instant
    fun moreThanDay(instant: Instant): Boolean
}