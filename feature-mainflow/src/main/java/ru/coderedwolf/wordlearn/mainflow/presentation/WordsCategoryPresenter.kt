package ru.coderedwolf.wordlearn.mainflow.presentation

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.common.di.PerFragment
import ru.coderedwolf.wordlearn.common.presentation.BasePresenter
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.wordscategory.domain.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.wordscategory.model.WordCategory
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
@PerFragment
@InjectViewState
class WordsCategoryPresenter @Inject constructor(
    private val flowRouter: FlowRouter,
    private val wordsCategoryInteractor: WordsCategoryInteractor,
    private val flows: WordsCategoryReachableFlows
) : BasePresenter<WordsCategoryView>() {

    private val categoryList = mutableListOf<WordCategory>()

    override fun onFirstViewAttach() = launchUI {
        viewState.showLoading(true)
        val list = wordsCategoryInteractor.findAllCategory()
        viewState.addAllCategory(list)
        categoryList.addAll(list)
        viewState.showLoading(false)
    }

    override fun onViewAttach(view: WordsCategoryView?) {
        viewState.updateCategoryList(categoryList)
    }

    fun onCreateCategory(categoryName: String) = launchUI {
        val category = WordCategory(name = categoryName, isStudy = false)
        val insertedCategory = wordsCategoryInteractor.addCategory(category)
        categoryList.add(0, insertedCategory)
        viewState.addCategory(0, insertedCategory)
    }

    fun onClickCategory(wordCategory: WordCategory) =
        flowRouter.startFlow(flows.wordFlow(wordCategory.id!!, wordCategory.name))

    fun onClickAddCategory() = viewState.showCreateCategoryDialog()

    override fun onBackPressed() = flowRouter.finishFlow()
}