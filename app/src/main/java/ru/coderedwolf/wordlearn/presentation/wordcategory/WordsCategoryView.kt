package ru.coderedwolf.wordlearn.presentation.wordcategory

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.model.Category

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface WordsCategoryView : MvpView {

    fun showLoading(show: Boolean)

    fun addCategory(index: Int, category: Category)
    fun addAllCategory(list: List<Category>)
    fun updateCategoryList(list: List<Category>)

    fun showCreateCategoryDialog()
}