package ru.coderedwolf.wordlearn.wordflow.presentation

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.common.presentation.BasePresenter
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.wordflow.domain.interactor.WordInteractor
import ru.coderedwolf.wordlearn.wordflow.ui.WordFlowScreens
import ru.coderedwolf.wordlearn.wordscategory.domain.WordsCategoryInteractor
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
@InjectViewState
class WordListPresenter @Inject constructor(
    private val categoryId: Long,
    private val categoryName: String,
    private val flowRouter: FlowRouter,
    private val wordInteractor: WordInteractor,
    private val wordsCategoryInteractor: WordsCategoryInteractor
) : BasePresenter<WordListView>() {

    override fun onViewAttach(view: WordListView?) = launchUI {
        viewState.showLoading(true)
        viewState.showTitle(categoryName)
        val list = wordInteractor.findAllPreviewByCategoryId(categoryId)
        viewState.showWords(list)
        viewState.showLoading(false)
    }

    override fun onBackPressed() = flowRouter.finishFlow()

    fun onClickAddWord() = flowRouter.navigateTo(WordFlowScreens.CreateWord)

    fun onClickActionLearn(checked: Boolean) = launchUI {
        wordsCategoryInteractor.changeLearnStatus(categoryId, checked)
    }
}