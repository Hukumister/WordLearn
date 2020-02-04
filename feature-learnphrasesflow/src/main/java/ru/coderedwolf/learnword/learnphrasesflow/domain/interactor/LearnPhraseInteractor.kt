package ru.coderedwolf.learnword.learnphrasesflow.domain.interactor

import ru.coderedwolf.wordlearn.phrase.model.LearnPhrase
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic
import javax.inject.Inject

/**
 * @author HaronCode. Date 14.06.2019.
 */
interface LearnPhraseInteractor {
    suspend fun incReviewCountPhrase(phraseId: Long): Boolean
    suspend fun decReviewCountPhrase(phraseId: Long): Boolean
    suspend fun findLearnPhraseGroupByTopic(shuffled: Boolean): Map<PhraseTopic, List<LearnPhrase>>
}

class LearnPhraseInteractorMock @Inject constructor() : LearnPhraseInteractor {
    override suspend fun incReviewCountPhrase(phraseId: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun decReviewCountPhrase(phraseId: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findLearnPhraseGroupByTopic(shuffled: Boolean): Map<PhraseTopic, List<LearnPhrase>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
