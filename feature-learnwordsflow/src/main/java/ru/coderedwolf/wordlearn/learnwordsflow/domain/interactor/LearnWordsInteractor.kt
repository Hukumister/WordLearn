package ru.coderedwolf.wordlearn.learnwordsflow.domain.interactor

import ru.coderedwolf.wordlearn.learnwordsflow.di.BatchSizeMemorizedWord
import ru.coderedwolf.wordlearn.learnwordsflow.di.BatchSizeNewWord
import ru.coderedwolf.wordlearn.learnwordsflow.domain.repository.LearnWordRepository
import ru.coderedwolf.wordlearn.learnwordsflow.model.LearnWord
import ru.coderedwolf.wordlearn.word.domain.repository.WordRepository
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
interface LearnWordsInteractor {
    suspend fun findAllLearnWords(): List<LearnWord>
    suspend fun incReview(learnWord: LearnWord)
    suspend fun markNotLearn(learnWord: LearnWord)
}

class LearnWordsInteractorImpl @Inject constructor(
    @BatchSizeNewWord private val batchSizeNewWord: Int,
    @BatchSizeMemorizedWord private val batchSizeMemorizedWord: Int,
    private val wordRepository: WordRepository,
    private val learnWordRepository: LearnWordRepository
) : LearnWordsInteractor {

    override suspend fun findAllLearnWords(): List<LearnWord> {
        val member = learnWordRepository.findMemberWordGroupByCategory(batchSizeMemorizedWord)
        val new = learnWordRepository.findNewWordGroupByCategory(batchSizeNewWord)

        val memberLearnList = convertToLearnList(member, false)
        val newLearnList = convertToLearnList(new, true)

        return memberLearnList + newLearnList
    }

    private fun convertToLearnList(
        member: Map<ru.coderedwolf.wordlearn.wordscategory.model.WordCategory, List<ru.coderedwolf.wordlearn.word.model.Word>>,
        isNew: Boolean
    ): List<LearnWord> = member.map { entry ->
        entry.value.map { LearnWord(it, entry.key.name, isNew) }
    }.flatten()

    override suspend fun incReview(learnWord: LearnWord) {
        wordRepository.incReviewCount(learnWord.word.wordId!!)
    }

    override suspend fun markNotLearn(learnWord: LearnWord) {
        wordRepository.setIsStudy(learnWord.word.wordId!!, false)
    }
}