package ru.coderedwolf.wordlearn.ui.wordscategory

import com.arellomobile.mvp.MvpView
import ru.coderedwolf.wordlearn.model.Category

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
interface WordsCategoryView : MvpView {

    fun showLoading(show: Boolean)
    fun showCategory(category: Category, index: Int)
    fun showCategoryList(list: List<Category>)
    fun showCreateCategoryDialog()
}