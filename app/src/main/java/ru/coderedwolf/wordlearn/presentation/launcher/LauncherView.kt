package ru.coderedwolf.wordlearn.presentation.launcher

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

/**
 * @author CodeRedWolf. Date 20.06.2019.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface LauncherView : MvpView {
    fun blockLoading(show: Boolean)
}