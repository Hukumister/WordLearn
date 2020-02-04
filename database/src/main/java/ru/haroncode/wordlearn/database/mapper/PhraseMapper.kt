package ru.haroncode.wordlearn.database.mapper

import ru.haroncode.wordlearn.database.entity.PhraseEntity
import ru.haroncode.wordlearn.phrase.model.LearnPhrase
import ru.haroncode.wordlearn.phrase.model.Phrase

/**
 * @author HaronCode.
 */
class PhraseMapper {

    fun convertList(list: List<PhraseEntity>) = list.map(::convert)

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

    fun convertListToEntity(list: List<Phrase>) = list.map(::convertToEntity)

    fun convertToEntity(phrase: Phrase) = PhraseEntity(
            id = phrase.id,
            topicId = phrase.topicId,
            translation = phrase.translation,
            lastReviewDate = phrase.lastReviewDate,
            reviewCount = phrase.reviewCount,
            textPhrase = phrase.textPhrase
    )
}