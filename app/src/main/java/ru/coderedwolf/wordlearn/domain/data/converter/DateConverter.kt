package ru.coderedwolf.wordlearn.domain.data.converter

import androidx.room.TypeConverter
import org.threeten.bp.Instant

class DateConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? = date?.toEpochMilli()
}