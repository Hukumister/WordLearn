package ru.haroncode.wordlearn.onboarding.domain.repository

import android.content.res.AssetManager
import de.siegmar.fastcsv.reader.CsvReader
import io.reactivex.Single
import javax.inject.Inject
import ru.haroncode.wordlearn.word.model.Word

/**
 * @author HaronCode.
 */
interface WordAssetRepository {
    fun findAllFor(categoryId: Long): Single<List<Word>>
}

class WordAssetRepositoryImpl @Inject constructor(
    private val assetManager: AssetManager,
    private val csvReader: CsvReader
) : WordAssetRepository {

    companion object {

        private const val WORDS_FILE_NAME = "words.csv"
    }

    override fun findAllFor(categoryId: Long): Single<List<Word>> = Single.never()
}
