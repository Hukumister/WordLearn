package ru.coderedwolf.wordlearn.di.provider

import android.content.Context
import androidx.room.Room
import ru.coderedwolf.wordlearn.database.DataBase
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
class DataBaseProvider @Inject constructor(
    private val context: Context
) : Provider<DataBase> {

    companion object {
        private const val DATA_BASE_NAME = "WordLearnDataBase"
    }

    override fun get(): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, DATA_BASE_NAME)
            .build()
    }
}