package ru.coderedwolf.wordlearn.presentation.word

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.model.WordPreview

/**
 * @author CodeRedWolf. Date 04.06.2019.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface WordListView : MvpView {

    fun showLoading(show: Boolean)
    fun showEmptyPlaceHolder(show: Boolean)

    fun showTitle(title: String)
    fun showWords(list: List<WordPreview>)
}