package ru.coderedwolf.wordlearn.domain.mappers

import ru.coderedwolf.wordlearn.model.Word
import ru.coderedwolf.wordlearn.model.WordPreview
import ru.coderedwolf.wordlearn.model.entity.WordEntity
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 09.06.2019.
 */

class WordMapper @Inject constructor() {

    fun convert(wordEntity: WordEntity): Word {
        return Word(
                wordId = wordEntity.wordId,
                categoryId = wordEntity.categoryId,
                examplesList = wordEntity.examplesList,
                translation = wordEntity.translation,
                transcription = wordEntity.transcription,
                word = wordEntity.word,
                reviewCount = wordEntity.reviewCount,
                association = wordEntity.phraseToMemorize,
                lastReviewDate = wordEntity.lastReviewDate
        )
    }

    fun convertToPreview(wordEntity: WordEntity): WordPreview {
        return WordPreview(
                word = wordEntity.word,
                wordId = wordEntity.wordId!!,
                reviewCount = wordEntity.reviewCount,
                translation = wordEntity.translation
        )
    }

    fun convertToEntity(word: Word): WordEntity {
        return WordEntity(
                wordId = word.wordId,
                categoryId = word.categoryId,
                examplesList = word.examplesList,
                translation = word.translation,
                transcription = word.transcription,
                word = word.word,
                reviewCount = word.reviewCount,
                phraseToMemorize = word.association,
                lastReviewDate = word.lastReviewDate
        )
    }
}