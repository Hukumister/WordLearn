package ru.coderedwolf.wordlearn.base

import android.os.Bundle
import ru.coderedwolf.wordlearn.moxy.androidx.MvpAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

abstract class RoutingMvpActivity : MvpAppCompatActivity() {

    private lateinit var mNavigator: Navigator
    private lateinit var mNavigationHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mNavigator = getNavigator()
        mNavigationHolder = getNavigationHolder()
    }

    abstract fun getNavigator(): Navigator

    abstract fun getNavigationHolder(): NavigatorHolder

    override fun onResumeFragments() {
        super.onResumeFragments()
        mNavigationHolder.setNavigator(mNavigator)
    }

    override fun onPause() {
        super.onPause()
        mNavigationHolder.removeNavigator()
    }
}