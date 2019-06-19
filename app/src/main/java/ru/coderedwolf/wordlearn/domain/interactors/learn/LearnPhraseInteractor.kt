package ru.coderedwolf.wordlearn.domain.interactors.learn

import ru.coderedwolf.wordlearn.domain.reporitory.LearnPhraseRepository
import ru.coderedwolf.wordlearn.domain.reporitory.PhraseRepository
import ru.coderedwolf.wordlearn.domain.system.TimeProvider
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase
import ru.coderedwolf.wordlearn.model.phrase.Phrase
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
interface LearnPhraseInteractor {

    suspend fun incReviewCountPhrase(phraseId: Long): Boolean
    suspend fun decReviewCountPhrase(phraseId: Long): Boolean

    suspend fun findLearnPhraseGroupByTopic(): Map<PhraseTopic, List<LearnPhrase>>
}


class LearnPhraseInteractorImpl @Inject constructor(
        private val timeProvider: TimeProvider,
        private val phraseRepository: PhraseRepository,
        private val learnPhraseRepository: LearnPhraseRepository
) : LearnPhraseInteractor {

    override suspend fun incReviewCountPhrase(phraseId: Long): Boolean {
        val phrase = phraseRepository.findOne(phraseId)
        return updateIfMoreThanDay(phrase, phrase.reviewCount + 1)
    }

    override suspend fun decReviewCountPhrase(phraseId: Long): Boolean {
        val phrase = phraseRepository.findOne(phraseId)
        return updateIfMoreThanDay(phrase, phrase.reviewCount - 1)
    }

    override suspend fun findLearnPhraseGroupByTopic(): Map<PhraseTopic, List<LearnPhrase>> = learnPhraseRepository
            .findLearnPhraseGroupByTopic()

    private suspend fun updateIfMoreThanDay(phrase: Phrase, newCount: Int): Boolean {
        return if (phrase.lastReviewDate == null || timeProvider.moreThanDay(phrase.lastReviewDate)) {
            phraseRepository.update(
                    phrase.copy(
                            reviewCount = newCount,
                            lastReviewDate = timeProvider.nowTime
                    )
            )
            true
        } else {
            false
        }
    }
}
