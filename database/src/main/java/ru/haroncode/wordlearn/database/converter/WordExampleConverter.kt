package ru.haroncode.wordlearn.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.haroncode.wordlearn.word.model.WordExample

/**
 * @author HaronCode.
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