package ru.coderedwolf.wordlearn.domain.repository

import android.content.res.AssetManager
import de.siegmar.fastcsv.reader.CsvReader
import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.extension.asSequence
import ru.coderedwolf.wordlearn.model.word.Word
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
interface WordAssetRepository {

    suspend fun findAllFor(categoryId: Long): List<Word>
}

class WordAssetRepositoryImpl @Inject constructor(
        private val assetManager: AssetManager,
        private val csvReader: CsvReader,
        private val dispatchersProvider: DispatchersProvider
) : WordAssetRepository {

    companion object {

        private const val WORDS_FILE_NAME = "words.csv"
    }

    override suspend fun findAllFor(categoryId: Long): List<Word> = withContext(dispatchersProvider.io()) {
        val stream = assetManager.open(WORDS_FILE_NAME)
        InputStreamReader(stream).use {
            val csvParser = csvReader.parse(it)
            return@withContext csvParser.asSequence()
                    .map { row ->
                        Word(
                                categoryId = categoryId,
                                word = row.getField(0),
                                translation = row.getField(1),
                                association = row.getField(2).orEmpty(),
                                reviewCount = 0,
                                transcription = "",
                                examplesList = emptyList()
                        )
                    }
                    .toList()
        }
    }
}