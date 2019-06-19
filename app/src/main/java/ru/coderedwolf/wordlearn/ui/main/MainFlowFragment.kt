package ru.coderedwolf.wordlearn.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_main.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.di.DI
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
class MainFlowFragment : BaseFragment() {

    override val layoutRes = R.layout.fragment_main

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override val parentScopeName = DI.APP_SCOPE

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
                    Screens.LearnMainScreen.screenKey -> Screens.LearnMainScreen
                    Screens.WordsCategoryScreen.screenKey -> Screens.WordsCategoryScreen
                    else -> Screens.PhraseTopicScreen
                }
        )
    }

    private fun selectTab(tab: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) add(R.id.childFragmentContainer, createTabFragment(tab), tab.screenKey)

            currentFragment?.let {
                hide(it)
            }
            newFragment?.let {
                show(it)
            }
        }.commitNow()
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed()
    }
}