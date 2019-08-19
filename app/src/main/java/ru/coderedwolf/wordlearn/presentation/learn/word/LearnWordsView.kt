package ru.coderedwolf.wordlearn.presentation.learn.word

import moxy.MvpView
import ru.coderedwolf.wordlearn.model.learn.LearnWord

/**
 * @author CodeRedWolf. Date 22.06.2019.
 */
interface LearnWordsView : MvpView {

    fun showLoading(show: Boolean)

    fun addAll(learnList: List<LearnWord>)
    fun add(learnWord: LearnWord)
    fun showTitle(title: String)
    fun showSubTitle(subTitle: String)
}