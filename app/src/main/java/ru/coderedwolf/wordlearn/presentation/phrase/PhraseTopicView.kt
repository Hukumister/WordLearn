package ru.coderedwolf.wordlearn.presentation.phrase

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.model.phrase.PhraseTopic

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface PhraseTopicView : MvpView {

    fun showAll(list: List<PhraseTopic>)
}