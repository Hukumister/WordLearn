package ru.coderedwolf.wordlearn.database.converter

import androidx.room.TypeConverter
import org.threeten.bp.Instant

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? = value?.let(Instant::ofEpochMilli)

    @TypeConverter
    fun instantToTimestamp(instant: Instant?): Long? = instant?.toEpochMilli()
}