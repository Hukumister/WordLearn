package ru.coderedwolf.wordlearn.learnwordsflow.presentation

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.common.di.PerFlow
import ru.coderedwolf.wordlearn.common.presentation.BasePresenter
import ru.coderedwolf.wordlearn.learnwordsflow.domain.interactor.LearnWordsInteractor
import ru.coderedwolf.wordlearn.learnwordsflow.model.LearnWord
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author HaronCode. Date 22.06.2019.
 */
@PerFlow
@InjectViewState
class LearnWordsPresenter @Inject constructor(
    private val router: Router,
    private val learnWordsInteractor: LearnWordsInteractor
) : BasePresenter<LearnWordsView>() {

    override fun onFirstViewAttach() = launchUI {
        viewState.showLoading(true)
        val learnList = learnWordsInteractor
            .findAllLearnWords()
            .shuffled()

        if (learnList.isNotEmpty()) {
            viewState.showTitle(learnList.first().categoryName)
            viewState.showSubTitle("Пройдено ${learnList.first().word.reviewCount}")
        }

        viewState.addAll(learnList)
        viewState.showLoading(false)
    }

    fun onWordAppear(learnWord: LearnWord) {
        viewState.showTitle(learnWord.categoryName)
        viewState.showSubTitle("Пройдено ${learnWord.word.reviewCount}")
    }

    override fun onBackPressed() = router.exit()

    fun onGotIt(learnWord: LearnWord) = when {
        learnWord.isNewWord -> markNotLearnWord(learnWord)
        learnWord.isRotate -> incReviewCount(learnWord)
        else -> {
            learnWord.isRotate = true
            viewState.add(learnWord)
        }
    }

    fun onMissIt(learnWord: LearnWord) = when {
        learnWord.isNewWord -> {
            learnWord.isNewWord = false
            viewState.add(learnWord)
        }
        learnWord.isRotate -> {
            learnWord.isRotate = false
            viewState.add(learnWord)
        }
        else -> viewState.add(learnWord)
    }

    fun onLoadMore() = launchUI {
        val learnList = learnWordsInteractor
            .findAllLearnWords()
            .shuffled()

        viewState.addAll(learnList)
    }

    private fun incReviewCount(learnWord: LearnWord) = launchUI {
        learnWordsInteractor.incReview(learnWord)
    }

    private fun markNotLearnWord(learnWord: LearnWord) = launchUI {
        learnWordsInteractor.markNotLearn(learnWord)
    }
}
