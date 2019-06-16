package ru.coderedwolf.wordlearn.di.provider

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.interactors.init.PrePopulateDataBaseInteractor
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
class DataBaseProvider @Inject constructor(
        private val context: Context,
        private val prePopulateDataBaseInteractor: PrePopulateDataBaseInteractor
) : Provider<DataBase> {

    companion object {
        private const val DATA_BASE_NAME = "WordLearnDataBase"
    }

    override fun get(): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, DATA_BASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch { prePopulateDataBaseInteractor.fullDataBase() }
                    }
                })
                .build()
    }
}