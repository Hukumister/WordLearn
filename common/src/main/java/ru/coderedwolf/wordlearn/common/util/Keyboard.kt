package ru.coderedwolf.wordlearn.common.util

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService


object Keyboard {

    /**
     * Explicitly request that the current input method's soft input area be
     * shown to the user, if needed.  Call this if the user interacts with
     * your view in such a way that they have expressed they would like to
     * start performing input into it.
     *
     * @param v         The currently focused view, which would like to receive
     * soft keyboard input.
     * @param forceShow Provides additional operating flags.
     */
    @JvmStatic
    @JvmOverloads
    fun show(v: View?, forceShow: Boolean = false) {
        if (v != null) {
            val imm = getInputMethodManager(v.context)
            if (imm != null) {
                if (forceShow) {
                    v.requestFocus()
                }
                val flags = if (forceShow) InputMethodManager.SHOW_FORCED else InputMethodManager.SHOW_IMPLICIT
                imm.showSoftInput(v, flags)
            }
        }
    }

    /**
     * @see .hide
     */
    @JvmStatic
    fun hide(dialog: Dialog?): Boolean {
        if (dialog != null) {
            //Find the currently focused view, so we can grab the correct window token from it.
            val view = dialog.currentFocus
            return hide(view)
        }
        return false
    }

    /**
     * Request to hide the soft input window from the context of the window
     * that is currently accepting input.  This should be called as a result
     * of the user doing some actually than fairly explicitly requests to
     * have the input window hidden.
     *
     * @param v The currently focused view, which would like to lost
     * soft keyboard input.
     * @return true if soft keyboard input was active, false otherwise.
     */
    @JvmStatic
    fun hide(v: View?): Boolean {
        var isHided = false
        if (v != null) {
            val imm = getInputMethodManager(v.context)
            if (imm != null) {
                isHided = imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
            }
        }
        return isHided
    }

    private fun getInputMethodManager(context: Context) = context.getSystemService<InputMethodManager>()

}
