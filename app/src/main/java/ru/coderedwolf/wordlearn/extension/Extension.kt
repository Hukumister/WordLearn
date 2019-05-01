package ru.coderedwolf.wordlearn.extension

import ru.coderedwolf.wordlearn.ui.base.BaseFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */
fun BaseFragment.scopeName(): String {
    return "${javaClass.simpleName}_${hashCode()}"
}

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}