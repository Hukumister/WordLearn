package ru.coderedwolf.wordlearn.learnwordsflow.domain.repository

import ru.coderedwolf.wordlearn.word.model.Word
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author HaronCode. Date 14.06.2019.
 */
interface LearnWordRepository {
    suspend fun findMemberWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>>
    suspend fun findNewWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>>
}

class LearnWordRepositoryMock @Inject constructor() : LearnWordRepository {
    override suspend fun findMemberWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findNewWordGroupByCategory(limit: Int): Map<WordCategory, List<Word>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}