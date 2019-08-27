package ru.coderedwolf.wordlearn.domain.repository

import android.content.res.AssetManager
import de.siegmar.fastcsv.reader.CsvReader
import de.siegmar.fastcsv.reader.CsvRow
import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.common.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.phrase.model.Phrase
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
interface PhraseAssetRepository {
    suspend fun findAllGroupByTopic(): Map<PhraseTopic, List<Phrase>>
}

class PhraseAssetRepositoryImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val csvReader: CsvReader,
    private val dispatchersProvider: DispatchersProvider
) : PhraseAssetRepository {

    companion object {

        private const val PHRASES_FILE_NAME = "phrases.csv"
    }

    //todo refactor
    override suspend fun findAllGroupByTopic(): Map<PhraseTopic, List<Phrase>> = withContext(dispatchersProvider.io()) {
        val stream = assetManager.open(PHRASES_FILE_NAME)
        InputStreamReader(stream).use {
            val csvParser = csvReader.parse(it)
            var nextRow: CsvRow?

            var topic: PhraseTopic? = null
            val result = mutableMapOf<PhraseTopic, List<Phrase>>()
            val list: MutableList<Phrase> = mutableListOf()
            do {
                nextRow = csvParser.nextRow()
                nextRow?.let { row ->
                    if (row.fields.size == 1) {
                        topic?.let { topic -> result[topic] = list.toList() }
                        list.clear()
                        topic = PhraseTopic(title = row.getField(0).trim(), isStudy = true)
                    } else {
                        list.add(
                            Phrase(
                                topicId = 0,
                                textPhrase = row.getField(0).trim(),
                                translation = row.getField(1).trim(),
                                reviewCount = 0,
                                lastReviewDate = null
                            )
                        )
                    }
                }

            } while (nextRow != null)
            topic?.let { topic -> result[topic] = list.toList() }
            result.toMap()
        }
    }
}