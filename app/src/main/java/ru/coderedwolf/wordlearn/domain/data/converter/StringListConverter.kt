package ru.coderedwolf.wordlearn.domain.data.converter

import androidx.room.TypeConverter

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class StringListConverter {

    @TypeConverter
    fun StringToList(value: String?): List<String> = value.orEmpty().split(Regex("\$;"))

    @TypeConverter
    fun listToString(list: List<String>): String = list.joinToString(separator = "$;")
}