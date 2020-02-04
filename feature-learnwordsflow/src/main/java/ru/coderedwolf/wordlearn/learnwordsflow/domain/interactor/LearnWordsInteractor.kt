package ru.coderedwolf.wordlearn.learnwordsflow.domain.interactor

import ru.coderedwolf.wordlearn.learnwordsflow.model.LearnWord
import javax.inject.Inject

/**
 * @author HaronCode. Date 22.06.2019.
 */
interface LearnWordsInteractor {
    suspend fun findAllLearnWords(): List<LearnWord>
    suspend fun incReview(learnWord: LearnWord)
    suspend fun markNotLearn(learnWord: LearnWord)
}

class LearnWordsInteractorMock @Inject constructor() : LearnWordsInteractor {
    override suspend fun findAllLearnWords(): List<LearnWord> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun incReview(learnWord: LearnWord) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun markNotLearn(learnWord: LearnWord) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}