package ru.coderedwolf.wordlearn.database.mapper

import ru.coderedwolf.wordlearn.database.entity.PhraseEntity
import ru.coderedwolf.wordlearn.phrase.model.LearnPhrase
import ru.coderedwolf.wordlearn.phrase.model.Phrase

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
class PhraseMapper {

    fun convert(phraseEntity: PhraseEntity) = Phrase(
        id = phraseEntity.id,
        topicId = phraseEntity.topicId,
        translation = phraseEntity.translation,
        lastReviewDate = phraseEntity.lastReviewDate,
        reviewCount = phraseEntity.reviewCount,
        textPhrase = phraseEntity.textPhrase
    )

    fun convertToLearn(topicTitle: String, phraseEntity: PhraseEntity) = LearnPhrase(
        phraseId = phraseEntity.id ?: -1,
        topicTitle = topicTitle,
        phraseText = phraseEntity.textPhrase,
        phraseTranslate = phraseEntity.translation
    )

    fun convertToEntity(phrase: Phrase) = PhraseEntity(
        id = phrase.id,
        topicId = phrase.topicId,
        translation = phrase.translation,
        lastReviewDate = phrase.lastReviewDate,
        reviewCount = phrase.reviewCount,
        textPhrase = phrase.textPhrase
    )
}