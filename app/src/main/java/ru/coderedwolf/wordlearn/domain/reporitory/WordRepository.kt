package ru.coderedwolf.wordlearn.domain.reporitory

import kotlinx.coroutines.withContext
import ru.coderedwolf.wordlearn.domain.data.DataBase
import ru.coderedwolf.wordlearn.domain.system.DispatchersProvider
import ru.coderedwolf.wordlearn.model.Word
import ru.coderedwolf.wordlearn.model.WordPreview
import ru.coderedwolf.wordlearn.model.entity.WordEntity
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
interface WordRepository {

    suspend fun findAllPreviewByCategoryId(categoryId: Long): List<WordPreview>

    suspend fun findAllByCategoryId(categoryId: Long): List<Word>
}

class WordRepositoryImpl @Inject constructor(
        dataBase: DataBase,
        private val dispatchersProvider: DispatchersProvider
) : WordRepository {

    private val wordDao = dataBase.wordDao()

    override suspend fun findAllPreviewByCategoryId(
            categoryId: Long
    ): List<WordPreview> = withContext(dispatchersProvider.io()) {
        wordDao.findAllByCategory(categoryId).map { it.toPreview() }
    }

    override suspend fun findAllByCategoryId(
            categoryId: Long
    ): List<Word> = TODO()
}

private fun WordEntity.toPreview(): WordPreview {
    return WordPreview(
            wordId = wordId!!,
            reviewCount = reviewCount,
            translate = translate,
            word = word
    )
}