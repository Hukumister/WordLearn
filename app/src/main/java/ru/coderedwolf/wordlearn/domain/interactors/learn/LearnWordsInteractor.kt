package ru.coderedwolf.wordlearn.domain.interactors.learn

import ru.coderedwolf.wordlearn.di.BatchSizeMemorizedWord
import ru.coderedwolf.wordlearn.di.BatchSizeNewWord
import ru.coderedwolf.wordlearn.di.PrimitiveWrapper
import ru.coderedwolf.wordlearn.domain.repository.LearnWordRepository
import ru.coderedwolf.wordlearn.domain.repository.WordRepository
import ru.coderedwolf.wordlearn.model.learn.LearnWord
import ru.coderedwolf.wordlearn.model.word.Word
import ru.coderedwolf.wordlearn.model.word.WordCategory
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
        @BatchSizeNewWord batchSizeNewWordWrapper: PrimitiveWrapper<Int>,
        @BatchSizeMemorizedWord batchSizeMemorizedWordWrapper: PrimitiveWrapper<Int>,
        private val wordRepository: WordRepository,
        private val learnWordRepository: LearnWordRepository
) : LearnWordsInteractor {

    private val batchSizeNewWord = batchSizeNewWordWrapper.value
    private val batchSizeMemorizedWord = batchSizeMemorizedWordWrapper.value

    override suspend fun findAllLearnWords(): List<LearnWord> {
        val member = learnWordRepository.findMemberWordGroupByCategory(batchSizeMemorizedWord)
        val new = learnWordRepository.findNewWordGroupByCategory(batchSizeNewWord)

        val memberLearnList = convertToLearnList(member, false)
        val newLearnList = convertToLearnList(new, true)

        return memberLearnList + newLearnList
    }

    private fun convertToLearnList(
            member: Map<WordCategory, List<Word>>,
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