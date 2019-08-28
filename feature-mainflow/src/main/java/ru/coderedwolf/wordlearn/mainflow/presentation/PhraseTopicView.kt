package ru.coderedwolf.wordlearn.mainflow.presentation

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.coderedwolf.wordlearn.phrase.model.PhraseTopic

/**
 * @author CodeRedWolf. Date 16.06.2019.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface PhraseTopicView : MvpView {
    fun showAll(list: List<PhraseTopic>)
}