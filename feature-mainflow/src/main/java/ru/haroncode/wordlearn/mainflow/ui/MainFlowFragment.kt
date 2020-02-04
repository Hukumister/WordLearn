package ru.haroncode.wordlearn.mainflow.ui

import android.os.Bundle
import android.view.MenuItem
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.haroncode.wordlearn.common.di.ComponentDependenciesProvider
import ru.haroncode.wordlearn.common.di.HasChildDependencies
import ru.haroncode.wordlearn.common.ui.BaseFragment
import ru.haroncode.wordlearn.common.util.change
import ru.haroncode.wordlearn.common.util.into
import ru.haroncode.wordlearn.mainflow.R
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author HaronCode.
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
