package ru.coderedwolf.wordlearn.domain.mappers

import ru.coderedwolf.wordlearn.model.entity.PhraseEntity
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase
import ru.coderedwolf.wordlearn.model.phrase.Phrase
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseMapper @Inject constructor() {

    fun convert(phraseEntity: PhraseEntity): Phrase {
        return Phrase(
                id = phraseEntity.id,
                topicId = phraseEntity.topicId,
                translation = phraseEntity.translation,
                lastReviewDate = phraseEntity.lastReviewDate,
                reviewCount = phraseEntity.reviewCount,
                textPhrase = phraseEntity.textPhrase
        )
    }

    fun convertToLearn(topicTitle: String, phraseEntity: PhraseEntity): LearnPhrase {
        return LearnPhrase(
                phraseId = phraseEntity.id ?: -1,
                topicTitle = topicTitle,
                phraseText = phraseEntity.textPhrase,
                phraseTranslate = phraseEntity.translation
        )
    }

    fun convertToEntity(phrase: Phrase): PhraseEntity {
        return PhraseEntity(
                id = phrase.id,
                topicId = phrase.topicId,
                translation = phrase.translation,
                lastReviewDate = phrase.lastReviewDate,
                reviewCount = phrase.reviewCount,
                textPhrase = phrase.textPhrase
        )
    }
}