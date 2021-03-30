package ru.haroncode.wordlearn.mainflow.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import ru.haroncode.wordlearn.common.presentation.FlowRouter
import ru.haroncode.wordlearn.common.ui.BaseFragment
import ru.haroncode.wordlearn.common.ui.theme.RewiseTheme
import ru.haroncode.wordlearn.mainflow.presentation.LearnReachableFlows
import javax.inject.Inject

/**
 * @author HaronCode.
 */
class LearnFragment : BaseFragment() {

    @Inject
    lateinit var flowRouter: FlowRouter

    @Inject
    lateinit var flows: LearnReachableFlows

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        setContent {
            RewiseTheme {
                LearnScreen()
            }
        }
    }

    override fun onBackPressed() = flowRouter.finishFlow()
}
