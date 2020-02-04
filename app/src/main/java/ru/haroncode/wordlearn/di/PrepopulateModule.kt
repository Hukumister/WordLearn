package ru.haroncode.wordlearn.di

import dagger.Module
import dagger.Provides
import de.siegmar.fastcsv.reader.CsvReader
import javax.inject.Singleton

@Module
abstract class PrepopulateModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun provideCsvReader() = CsvReader().apply {
            setFieldSeparator(';')
        }
    }

}