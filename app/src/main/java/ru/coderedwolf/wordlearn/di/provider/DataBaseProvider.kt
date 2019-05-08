package ru.coderedwolf.wordlearn.di.provider

import android.content.Context
import androidx.room.Room
import ru.coderedwolf.wordlearn.domain.data.DataBase
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
class DataBaseProvider @Inject constructor(
        private val context: Context
) : Provider<DataBase> {

    override fun get(): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, "WordLearnDataBase")
                .build()
    }
}