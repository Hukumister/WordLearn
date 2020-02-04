package ru.haroncode.wordlearn.common.ui

import android.os.Bundle
import ru.haroncode.wordlearn.common.R
import ru.haroncode.wordlearn.common.extension.setLaunchScreen
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

abstract class FlowFragment : BaseFragment(R.layout.layout_container) {

    private val currentFragment: BaseFragment?
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    @Inject
    protected lateinit var navigatorHolder: NavigatorHolder

    @Inject
    protected lateinit var router: Router

    protected abstract val launchScreen: SupportAppScreen

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(activity, childFragmentManager, R.id.container) {
            override fun activityBack() = onExit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(launchScreen)
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: onExit()
    }

    private fun onExit() = router.exit()
}