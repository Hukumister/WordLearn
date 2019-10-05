@file:Suppress("NOTHING_TO_INLINE")

package ru.coderedwolf.wordlearn.common.util

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.android.support.SupportAppScreen

inline infix fun SupportAppScreen.into(@IdRes containerId: Int): Change =
    Change(containerId, this)

inline fun FragmentManager.transaction(
    allowStateLoss: Boolean = false,
    body: FragmentTransaction.() -> Unit
) = beginTransaction()
    .apply(body)
    .apply { if (allowStateLoss) commitNowAllowingStateLoss() else commitNow() }


data class Change(
    val containerId: Int,
    val newScreen: SupportAppScreen
)

inline fun FragmentManager.change(fragmentHolder: Change) {
    val newScreen = fragmentHolder.newScreen
    val currentFragment = fragments.firstOrNull { fragment -> !fragment.isHidden }
    val newFragment = findFragmentByTag(newScreen.screenKey)
    if (currentFragment != null && newFragment != null && currentFragment == newFragment) return
    transaction {
        if (newFragment == null) add(
            fragmentHolder.containerId,
            newScreen.fragment,
            newScreen.screenKey
        )
        currentFragment?.let(::hide)
        newFragment?.let(::show)
    }
}
