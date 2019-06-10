package ru.coderedwolf.wordlearn.presentation.word.create

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.domain.interactors.validator.Violation

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface CreateWordView : MvpView {

    fun showError(error: String)
    fun showFieldError(violation: Violation)

    fun removeExample(position: Int)
    fun addExampleView()
}