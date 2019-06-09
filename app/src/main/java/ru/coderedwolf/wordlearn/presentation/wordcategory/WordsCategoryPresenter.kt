package ru.coderedwolf.wordlearn.presentation.wordcategory

import moxy.InjectViewState
import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.presentation.base.BasePresenter
import ru.coderedwolf.wordlearn.domain.interactors.WordsCategoryInteractor
import ru.coderedwolf.wordlearn.model.Category
import ru.coderedwolf.wordlearn.ui.wordscategory.WordsCategoryItem
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
@InjectViewState
class WordsCategoryPresenter @Inject constructor(
        private val router: Router,
        private val wordsCategoryInteractor: WordsCategoryInteractor
) : BasePresenter<WordsCategoryView>() {

    private val categoryList = mutableListOf<Category>()

    override fun onFirstViewAttach() = launchUI {
        viewState.showLoading(true)
        val list = wordsCategoryInteractor.findAllCategory()
        viewState.addAllCategory(list)
        categoryList.addAll(list)
        viewState.showLoading(false)
    }

    override fun attachView(view: WordsCategoryView?) {
        super.attachView(view)
        viewState.updateCategoryList(categoryList)
    }

    fun onCreateCategory(categoryName: String) = launchUI {
        val category = Category(name = categoryName, isStudy = false)
        val insertedCategory = wordsCategoryInteractor.addCategory(category)
        categoryList.add(0, insertedCategory)
        viewState.addCategory(0, insertedCategory)
    }

    fun onClickCategory(category: Category) = router
            .navigateTo(Screens.WordScreenFlow(category.id!!, category.name))

    fun onClickAddCategory() = viewState.showCreateCategoryDialog()

    override fun onBackPressed() = router.finishChain()

    fun onCheckedCategory(categoryItem: WordsCategoryItem, checked: Boolean) {
        //todo сделать обновление статуса в базе
    }
}