package ru.coderedwolf.wordlearn.mainflow.ui

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.mainflow.R
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
class MainFlowFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_main_flow

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    private val mainFlowScreenFactory = MainFlowScreenFactory()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val screen = mainFlowScreenFactory.findScreen(item.itemId)
            if (screen.screenKey != currentTabFragment?.tag) {
                selectTab(screen)
            }
            true
        }

        if (currentTabFragment == null) {
            bottomNavigationView.selectedItemId = R.id.learn
        }

        selectTab(
            when (currentTabFragment?.tag) {
                MainFlowScreens.Learn.screenKey -> MainFlowScreens.Learn
                MainFlowScreens.WordsCategory.screenKey -> MainFlowScreens.WordsCategory
                else -> MainFlowScreens.PhraseTopic
            }
        )
    }

    private fun selectTab(tab: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) add(R.id.childFragmentContainer, tab.fragment, tab.screenKey)

            currentFragment?.let {
                hide(it)
            }
            newFragment?.let {
                show(it)
            }
        }.commitNow()
    }

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed()
    }
}