package ru.coderedwolf.wordlearn.presentation.learn

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.domain.interactors.learn.LearnPhraseInteractor
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase
import ru.coderedwolf.wordlearn.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
@InjectViewState
class LearnPhrasesPresenter @Inject constructor(
        private val router: Router,
        private val learnPhraseInteractor: LearnPhraseInteractor
) : BasePresenter<LearnPhrasesView>() {

    override fun onFirstViewAttach() = launchUI {
        viewState.showListLoading(true)
        val flatten = learnPhraseInteractor
                .findLearnPhraseGroupByTopic()
                .values
                .flatten()

        viewState.addLearnList(flatten)
        viewState.showCategoryName(flatten.first().topicTitle)
        viewState.showListLoading(false)
    }

    override fun onBackPressed() = router.exit()

    fun onMissedPhrase(learnPhrase: LearnPhrase) = launchUI {
        learnPhraseInteractor.decReviewCountPhrase(learnPhrase.phraseId)
    }

    fun onGotPhrase(learnPhrase: LearnPhrase) = launchUI {
        learnPhraseInteractor.incReviewCountPhrase(learnPhrase.phraseId)
    }

    fun onCardAppeared(learnPhrase: LearnPhrase) = viewState.showCategoryName(learnPhrase.topicTitle)

    fun onPhrasesGone() {
        viewState.showCategoryName("")
        viewState.showInfoCard()
    }
}