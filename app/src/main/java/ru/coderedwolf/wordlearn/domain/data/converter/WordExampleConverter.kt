package ru.coderedwolf.wordlearn.domain.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.coderedwolf.wordlearn.model.WordExample

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
class WordExampleConverter {

    private val converter = Gson()

    @TypeConverter
    fun stringToList(value: String?): List<WordExample> {
        if (value.isNullOrEmpty()) {
            return emptyList()
        }
        val listType = object : TypeToken<List<WordExample>>() {}.type
        return converter.fromJson(value, listType)
    }

    @TypeConverter
    fun listToString(list: List<WordExample>): String = converter.toJson(list)
}