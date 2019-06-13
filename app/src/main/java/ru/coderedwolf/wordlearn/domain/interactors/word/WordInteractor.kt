package ru.coderedwolf.wordlearn.domain.interactors.word

import ru.coderedwolf.wordlearn.domain.interactors.validator.WordValidator
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

    suspend fun addWord(word: Word): Word
}

class WordInteractorImpl @Inject constructor(
        private val wordValidator: WordValidator,
        private val wordRepository: WordRepository
) : WordInteractor {

    override suspend fun addWord(word: Word): Word {
        wordValidator.validate(word).throwIfHasError()
        return wordRepository.save(word)
    }

    override suspend fun findAllByCategoryId(
            categoryId: Long
    ): List<Word> = wordRepository
            .findAllByCategoryId(categoryId)

    override suspend fun findAllPreviewByCategoryId(
            categoryId: Long
    ): List<WordPreview> = wordRepository
            .findAllPreviewByCategoryId(categoryId)
}