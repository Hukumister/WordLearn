package ru.coderedwolf.wordlearn.domain.system

import org.threeten.bp.Duration
import org.threeten.bp.Instant
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 15.06.2019.
 */
interface TimeProvider {

    val nowTime: Instant

    fun moreThanDay(instant: Instant): Boolean
}

class SystemTimeProvider @Inject constructor() : TimeProvider {

    override val nowTime: Instant
        get() = Instant.now()

    override fun moreThanDay(instant: Instant): Boolean {
        return Duration.between(nowTime, instant).toHours() >= 12
    }
}