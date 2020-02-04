package ru.coderedwolf.wordlearn.learnwordsflow.presentation

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.learnwordsflow.model.LearnWord

/**
 * @author HaronCode. Date 22.06.2019.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface LearnWordsView : MvpView {
    fun showLoading(show: Boolean)
    fun addAll(learnList: List<LearnWord>)
    fun add(learnWord: LearnWord)
    fun showTitle(title: String)
    fun showSubTitle(subTitle: String)
}