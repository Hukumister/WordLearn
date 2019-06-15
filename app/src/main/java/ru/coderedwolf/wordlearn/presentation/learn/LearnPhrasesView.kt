package ru.coderedwolf.wordlearn.presentation.learn

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.model.learn.LearnPhrase

/**
 * @author CodeRedWolf. Date 14.06.2019.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface LearnPhrasesView : MvpView {

    fun showListLoading(show: Boolean)
    fun showCategoryName(categoryName: String)
    fun showInfoCard()

    fun addLearnList(list: List<LearnPhrase>)
}
