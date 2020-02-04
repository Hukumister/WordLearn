package ru.coderedwolf.wordlearn.common.extension

import androidx.fragment.app.FragmentManager
import ru.coderedwolf.wordlearn.common.ui.ProgressDialog
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

/**
 * @author HaronCode. Date 21.04.2019.
 */

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}

fun FragmentManager.showProgressDialog(show: Boolean) {
    val progressDialog = findFragmentByTag(ProgressDialog.TAG)
    when {
        progressDialog == null && show -> {
            ProgressDialog().show(this, ProgressDialog.TAG)
            executePendingTransactions()
        }
        progressDialog != null && !show -> {
            (progressDialog as? ProgressDialog)?.dismissAllowingStateLoss()
            executePendingTransactions()
        }
    }
}