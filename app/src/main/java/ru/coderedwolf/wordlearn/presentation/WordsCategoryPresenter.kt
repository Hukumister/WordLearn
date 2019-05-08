package ru.coderedwolf.wordlearn.presentation

import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.base.BasePresenter
import ru.coderedwolf.wordlearn.domain.interactors.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.model.Category
import ru.coderedwolf.wordlearn.ui.wordscategory.WordsCategoryView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
class WordsCategoryPresenter @Inject constructor(
        private val router: Router,
        private val wordsCategoryInteractor: WordsCategoryInteractor
) : BasePresenter<WordsCategoryView>() {

    override fun onFirstViewAttach() = launchUI {
        viewState.showLoading(true)
        val list = wordsCategoryInteractor.findAllCategory()
        viewState.showCategoryList(list)
        viewState.showLoading(false)
    }

    fun onCreateCategory(category: Category) = launchUI {
        val insertedCategory = wordsCategoryInteractor.addCategory(category)
        viewState.showCategory(insertedCategory, 0)
    }

    fun onClickCategory(category: Category) = router.navigateTo(Screens.CategoryWordsFlow(category.id!!))

    fun onClickAddCategory() = viewState.showCreateCategoryDialog()
}