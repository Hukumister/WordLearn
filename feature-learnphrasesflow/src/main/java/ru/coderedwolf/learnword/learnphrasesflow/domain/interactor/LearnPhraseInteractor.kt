package ru.coderedwolf.learnword.learnphrasesflow.domain.interactor

import ru.coderedwolf.learnword.learnphrasesflow.domain.repository.LearnPhraseRepository
import ru.coderedwolf.learnword.learnphrasesflow.domain.repository.PhraseRepository
import ru.coderedwolf.wordlearn.common.domain.system.TimeProvider
import ru.coderedwolf.wordlearn.phrase.model.LearnPhrase
import ru.coderedwolf.wordlearn.phrase.model.Phrase
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
interface LearnPhraseInteractor {
    suspend fun incReviewCountPhrase(phraseId: Long): Boolean
    suspend fun decReviewCountPhrase(phraseId: Long): Boolean
    suspend fun findLearnPhraseGroupByTopic(shuffled: Boolean): Map<PhraseTopic, List<LearnPhrase>>
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

    override suspend fun findLearnPhraseGroupByTopic(
        shuffled: Boolean
    ): Map<PhraseTopic, List<LearnPhrase>> = learnPhraseRepository.findLearnPhraseGroupByTopic(shuffled)

    private suspend fun updateIfMoreThanDay(phrase: Phrase, newCount: Int): Boolean {
        val lastReviewDate = phrase.lastReviewDate
        return if (lastReviewDate == null || timeProvider.moreThanDay(lastReviewDate)) {
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
