package ru.coderedwolf.wordlearn.domain.interactors

import ru.coderedwolf.wordlearn.domain.reporitory.WordRepository
import ru.coderedwolf.wordlearn.model.Word
import ru.coderedwolf.wordlearn.model.WordPreview
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 05.06.2019.
 */
interface WordInteractor {

    suspend fun findAllPreviewByCategoryId(categoryId: Long): List<WordPreview>
    suspend fun findAllByCategoryId(categoryId: Long): List<Word>
}

class WordInteractorImpl @Inject constructor(
        private val wordRepository: WordRepository
) : WordInteractor {

    override suspend fun findAllByCategoryId(
            categoryId: Long
    ): List<Word> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findAllPreviewByCategoryId(
            categoryId: Long
    ): List<WordPreview> = wordRepository
            .findAllPreviewByCategoryId(categoryId)
}