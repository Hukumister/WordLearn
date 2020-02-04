package ru.coderedwolf.wordlearn.database.mapper

import ru.coderedwolf.wordlearn.database.entity.WordEntity
import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.word.model.WordPreview

/**
 * @author HaronCode. Date 09.06.2019.
 */

class WordMapper {

    fun convert(wordEntity: WordEntity) = Word(
            wordId = wordEntity.wordId,
            categoryId = wordEntity.categoryId,
            examplesList = wordEntity.examplesList,
            translation = wordEntity.translation,
            isStudy = wordEntity.isStudy,
            word = wordEntity.word,
            reviewCount = wordEntity.reviewCount,
            association = wordEntity.phraseToMemorize,
            lastReviewDate = wordEntity.lastReviewDate
    )

    fun convertToEntity(word: Word) = WordEntity(
            wordId = word.wordId,
            categoryId = word.categoryId,
            examplesList = word.examplesList,
            translation = word.translation,
            word = word.word,
            isStudy = word.isStudy,
            reviewCount = word.reviewCount,
            phraseToMemorize = word.association,
            lastReviewDate = word.lastReviewDate
    )

    fun convertListToPreview(words: List<WordEntity>) = words.map(::convertToPreview)

    fun convertList(words: List<WordEntity>) = words.map(::convert)

    private fun convertToPreview(wordEntity: WordEntity) = WordPreview(
            word = wordEntity.word,
            wordId = wordEntity.wordId!!,
            reviewCount = wordEntity.reviewCount,
            translation = wordEntity.translation
    )
}