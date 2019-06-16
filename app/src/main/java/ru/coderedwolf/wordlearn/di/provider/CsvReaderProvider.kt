package ru.coderedwolf.wordlearn.di.provider

import de.siegmar.fastcsv.reader.CsvReader
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class CsvReaderProvider @Inject constructor() : Provider<CsvReader> {

    override fun get(): CsvReader = CsvReader().apply {
        setFieldSeparator(';')
    }
}