package ru.coderedwolf.wordlearn.mainflow.domain.interactor

import ru.coderedwolf.wordlearn.wordscategory.domain.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 02.05.2019.
 */
class WordsCategoryInteractorImpl @Inject constructor() : WordsCategoryInteractor {
    override suspend fun findAllCategory(): List<WordCategory> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun addCategory(wordCategory: WordCategory): WordCategory {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun remoteCategory(categoryId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun changeLearnStatus(categoryId: Long, isStudy: Boolean) {
        TODO("not implModuleemented") //To change body of created functions use File | Settings | File Templates.
    }

}
