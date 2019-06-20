package ru.coderedwolf.wordlearn.presentation.word.wordlist

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.di.FlowRouter
import ru.coderedwolf.wordlearn.di.PrimitiveWrapper
import ru.coderedwolf.wordlearn.di.provider.qualifier.CategoryId
import ru.coderedwolf.wordlearn.di.provider.qualifier.CategoryName
import ru.coderedwolf.wordlearn.domain.interactors.word.WordInteractor
import ru.coderedwolf.wordlearn.domain.interactors.word.category.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.presentation.base.BasePresenter
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
@InjectViewState
class WordListPresenter @Inject constructor(
        @CategoryId categoryIdWrapper: PrimitiveWrapper<Long>,
        @CategoryName categoryNameWrapper: PrimitiveWrapper<String>,
        private val flowRouter: FlowRouter,
        private val wordInteractor: WordInteractor,
        private val wordsCategoryInteractor: WordsCategoryInteractor
) : BasePresenter<WordListView>() {

    private val categoryId: Long = categoryIdWrapper.value
    private val categoryName: String = categoryNameWrapper.value

    override fun onViewAttach(view: WordListView?) = launchUI {
        viewState.showLoading(true)
        viewState.showTitle(categoryName)
        val list = wordInteractor.findAllPreviewByCategoryId(categoryId)
        viewState.showWords(list)
        viewState.showLoading(false)
    }

    override fun onBackPressed() = flowRouter.finishFlow()

    fun onClickAddWord() = flowRouter.navigateTo(Screens.WordCreateScreen)

    fun onClickActionLearn(checked: Boolean) = launchUI {
        wordsCategoryInteractor.changeLearnStatus(categoryId, checked)
    }
}