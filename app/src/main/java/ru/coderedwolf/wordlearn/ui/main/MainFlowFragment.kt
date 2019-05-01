package ru.coderedwolf.wordlearn.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_main.*
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.Screens
import ru.coderedwolf.wordlearn.di.DI
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.base.FlowFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppScreen
import toothpick.Toothpick
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
class MainFlowFragment : FlowFragment() {

    override val layoutRes = R.layout.fragment_main

    @Inject
    override lateinit var navigatorHolder: NavigatorHolder

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override val parentScopeName = DI.APP_SCOPE

    private val mainFlowScreenFactory = MainFlowScreenFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, scope)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val screen = mainFlowScreenFactory.findScreen(item.itemId)
            if (screen.screenKey != currentTabFragment?.tag) {
                selectTab(screen)
            }
            false
        }

        selectTab(
            when (currentTabFragment?.tag) {
                Screens.LearnScreen.screenKey -> Screens.LearnScreen
                Screens.WordsScreen.screenKey -> Screens.WordsScreen
                else -> Screens.PhrasesScreen
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
                it.userVisibleHint = false
            }
            newFragment?.let {
                show(it)
                it.userVisibleHint = true
            }
        }.commitNow()
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed()
    }
}