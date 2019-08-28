package ru.coderedwolf.learnword.learnphrasesflow.presentation

import moxy.InjectViewState
import ru.coderedwolf.learnword.learnphrasesflow.domain.interactor.LearnPhraseInteractor
import ru.coderedwolf.wordlearn.common.di.PerFlow
import ru.coderedwolf.wordlearn.common.presentation.BasePresenter
import ru.coderedwolf.wordlearn.phrase.model.LearnPhrase
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
@PerFlow
@InjectViewState
class LearnPhrasesPresenter @Inject constructor(
    private val router: Router,
    private val learnPhraseInteractor: LearnPhraseInteractor
) : BasePresenter<LearnPhrasesView>() {

    override fun onFirstViewAttach() = launchUI {
        viewState.showListLoading(true)
        val flatten = learnPhraseInteractor
            .findLearnPhraseGroupByTopic(true)
            .values
            .flatten()

        viewState.addLearnList(flatten)
        viewState.showCategoryName(flatten.firstOrNull()?.topicTitle.orEmpty())
        viewState.showListLoading(false)
    }

    override fun onBackPressed() = router.exit()

    fun onNext(learnPhrase: LearnPhrase) = launchUI {
        learnPhraseInteractor.incReviewCountPhrase(learnPhrase.phraseId)
    }

    fun onCardAppeared(learnPhrase: LearnPhrase) = viewState.showCategoryName(learnPhrase.topicTitle)

    fun onPhrasesGone() {
        viewState.showCategoryName("")
        viewState.showInfoCard()
    }
}