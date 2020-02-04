package ru.coderedwolf.wordlearn.mainflow.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_learn.*
import ru.coderedwolf.wordlearn.common.extension.onClick
import ru.coderedwolf.wordlearn.common.presentation.FlowRouter
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.mainflow.R
import ru.coderedwolf.wordlearn.mainflow.presentation.LearnReachableFlows
import javax.inject.Inject

/**
 * @author HaronCode. Date 14.06.2019.
 */
class LearnFragment : BaseFragment(R.layout.fragment_learn) {

    @Inject
    lateinit var flowRouter: FlowRouter

    @Inject
    lateinit var flows: LearnReachableFlows

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        wordsButton.onClick { flowRouter.startFlow(flows.learnWordsFlow()) }
        phraseButton.onClick { flowRouter.startFlow(flows.learnPhrasesFlow()) }
    }

    override fun onBackPressed() = flowRouter.finishFlow()
}