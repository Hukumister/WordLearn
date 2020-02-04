package ru.coderedwolf.wordlearn.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.common.di.ComponentManager.clearInjector
import ru.coderedwolf.wordlearn.common.di.ComponentManager.inject
import ru.coderedwolf.wordlearn.common.extension.setLaunchScreen
import ru.coderedwolf.wordlearn.common.ui.BaseFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

/**
 * @author HaronCode. Date 21.04.2019.
 */
class AppActivity : AppCompatActivity() {

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    private val navigator: Navigator = object : SupportAppNavigator(this, R.id.container) {
        override fun setupFragmentTransaction(
            command: Command?,
            currentFragment: Fragment?,
            nextFragment: Fragment?,
            fragmentTransaction: FragmentTransaction
        ) {
            //fix incorrect order lifecycle callback of MainFlowFragment
            fragmentTransaction.setReorderingAllowed(true)
        }
    }

    private val componentName
        get() = javaClass.name

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        inject(componentName)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)
        if (supportFragmentManager.fragments.isEmpty()) {
            navigator.setLaunchScreen(Flows.Main)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            clearInjector(componentName)
        }
    }
}