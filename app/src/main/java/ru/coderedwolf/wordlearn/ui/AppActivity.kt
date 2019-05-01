package ru.coderedwolf.wordlearn.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ru.coderedwolf.wordlearn.App
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.di.AppComponent
import ru.coderedwolf.wordlearn.moxy.androidx.MvpAppCompatActivity
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
class AppActivity : MvpAppCompatActivity() {

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? BaseFragment

    private val appComponent: AppComponent?
        get() = (application as? App)?.appComponent

    private val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.fragmentContainer) {
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

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        appComponent?.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)
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
}