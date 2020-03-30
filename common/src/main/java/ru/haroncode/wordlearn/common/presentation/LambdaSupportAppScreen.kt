package ru.haroncode.wordlearn.common.presentation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

open class LambdaSupportAppScreen(private val fragmentProvider: () -> Fragment) : SupportAppScreen() {

    override fun getFragment() = fragmentProvider()
}
