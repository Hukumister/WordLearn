package ru.coderedwolf.wordlearn.ui

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.coderedwolf.wordlearn.R
import ru.coderedwolf.wordlearn.di.DI
import ru.coderedwolf.wordlearn.presentation.launcher.Launcher
import ru.coderedwolf.wordlearn.presentation.launcher.LauncherView
import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.coderedwolf.wordlearn.ui.global.ProgressDialog
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import toothpick.Toothpick
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
class AppActivity : MvpAppCompatActivity(), LauncherView {

    companion object {

        private const val PROGRESS_DIALOG = "progress_dialog"
    }

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as? BaseFragment

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

    @Inject
    @InjectPresenter
    lateinit var launcher: Launcher

    @ProvidePresenter
    fun providePresenter(): Launcher = launcher

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        Toothpick.inject(this, Toothpick.openScope(DI.APP_SCOPE))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)
        if (savedInstanceState == null) {
            launcher.onCouldStart()
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

    override fun blockLoading(show: Boolean) {
        if (show) {
            ProgressDialog().show(supportFragmentManager, PROGRESS_DIALOG)
        } else {
            (supportFragmentManager.findFragmentByTag(PROGRESS_DIALOG) as? DialogFragment)
                    ?.dismissAllowingStateLoss()
        }
    }
}