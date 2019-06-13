package ru.coderedwolf.wordlearn.presentation.wordcategory

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.model.WordCategory

/**
 * @author CodeRedWolf. Date 01.05.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface WordsCategoryView : MvpView {

    fun showLoading(show: Boolean)

    fun addCategory(index: Int, wordCategory: WordCategory)
    fun addAllCategory(list: List<WordCategory>)
    fun updateCategoryList(list: List<WordCategory>)

    fun showCreateCategoryDialog()
}