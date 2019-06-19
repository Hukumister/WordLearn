package ru.coderedwolf.wordlearn.domain.system

import android.text.format.DateUtils
import java.util.*
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 15.06.2019.
 */
interface TimeProvider {

    val nowTime: Date

    fun moreThanDay(date: Date): Boolean
}

class SystemTimeProvider @Inject constructor() : TimeProvider {

    override val nowTime: Date
        get() = Date()

    override fun moreThanDay(date: Date): Boolean {
        return nowTime.time - date.time >= DateUtils.DAY_IN_MILLIS
    }
}