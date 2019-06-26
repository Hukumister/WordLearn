package ru.coderedwolf.wordlearn.presentation.learn.word

import ru.coderedwolf.wordlearn.domain.interactors.learn.LearnWordsInteractor
import ru.coderedwolf.wordlearn.model.learn.LearnWord
import ru.coderedwolf.wordlearn.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
class LearnWordsPresenter @Inject constructor(
        private val router: Router,
        private val learnWordsInteractor: LearnWordsInteractor
) : BasePresenter<LearnWordsView>() {

    override fun onFirstViewAttach() = launchUI {
        val learnList = learnWordsInteractor
                .findAllLearnWords()
                .shuffled()

        viewState.addAll(learnList)
    }

    fun onWordAppear(learnWord: LearnWord) = viewState.showTitle(learnWord.categoryName)

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
