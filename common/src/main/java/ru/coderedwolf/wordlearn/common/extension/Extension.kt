package ru.coderedwolf.wordlearn.common.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.FragmentManager
import ru.coderedwolf.wordlearn.common.ui.ProgressDialog
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

/**
 * @author CodeRedWolf. Date 21.04.2019.
 */

fun Navigator.setLaunchScreen(screen: SupportAppScreen) {
    applyCommands(
        arrayOf(
            BackTo(null),
            Replace(screen)
        )
    )
}

fun Activity.hideKeyboard() {
    currentFocus?.windowToken?.let {
        getSystemService<InputMethodManager>()?.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun Activity.showKeyboard() {
    currentFocus?.apply {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
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