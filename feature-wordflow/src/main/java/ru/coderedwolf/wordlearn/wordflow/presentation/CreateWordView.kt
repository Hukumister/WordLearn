package ru.coderedwolf.wordlearn.wordflow.presentation

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.common.domain.validator.Violation
import ru.coderedwolf.wordlearn.word.model.WordExample

/**
 * @author CodeRedWolf. Date 06.06.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface CreateWordView : MvpView {

    fun showError(error: String)
    fun showFieldError(violation: Violation)
    fun showDialogCreateExample()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun updateExampleList(list: List<WordExample>)
}