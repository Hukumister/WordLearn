package ru.coderedwolf.wordlearn.mainflow.ui

import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.coderedwolf.wordlearn.common.di.ComponentDependenciesProvider
import ru.coderedwolf.wordlearn.common.di.HasChildDependencies
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.coderedwolf.wordlearn.common.util.change
import ru.coderedwolf.wordlearn.common.util.into
import ru.coderedwolf.wordlearn.mainflow.R
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
class MainFlowFragment : BaseFragment(R.layout.fragment_main_flow), HasChildDependencies {

    @Inject override lateinit var dependencies: ComponentDependenciesProvider

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { fragment -> !fragment.isHidden } as? BaseFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (childFragmentManager.fragments.isEmpty()) {
            bottomNavigationView.selectedItemId = R.id.action_learn
            selectTab(MainFlowScreens.Learn)
        }
    }

    override fun onStart() {
        super.onStart()
        bottomNavigationView.setOnNavigationItemSelectedListener(::onBottomNavigationItemSelected)
        bottomNavigationView.setOnNavigationItemReselectedListener(::onBottomNavigationItemReselected)
    }

    override fun onStop() {
        bottomNavigationView.setOnNavigationItemSelectedListener(null)
        bottomNavigationView.setOnNavigationItemReselectedListener(null)
        super.onStop()
    }

    private fun onBottomNavigationItemReselected(item: MenuItem) =
        MainFlowScreenFactory.findScreen(item.itemId).let(::selectTab)

    private fun onBottomNavigationItemSelected(item: MenuItem): Boolean {
        MainFlowScreenFactory.findScreen(item.itemId).let(::selectTab)
        return true
    }

    private fun selectTab(tab: SupportAppScreen) = childFragmentManager.change(
        tab into R.id.childFragmentContainer
    )

    override fun onBackPressed() = currentTabFragment?.onBackPressed() ?: Unit
}