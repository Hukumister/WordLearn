package ru.coderedwolf.wordlearn.ui.wordscategory

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.layout_create_category_dialog.view.*
import ru.coderedwolf.wordlearn.R

/**
 * @author CodeRedWolf. Date 04.05.2019.
 */
class CreateCategoryDialog : DialogFragment() {

    private val listener: OnCreateCategory
        get() = when {
            parentFragment is OnCreateCategory -> parentFragment as OnCreateCategory
            activity is OnCreateCategory -> activity as OnCreateCategory
            else -> object : OnCreateCategory {}
        }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.layout_create_category_dialog, null)
        return AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.create_category_title))
                .setView(view)
                .setPositiveButton(R.string.create) { _, _ ->
                    val categoryName = view.categoryName.text.toString()
                    if (categoryName.isNotEmpty()) {
                        listener.onCreateCategory(categoryName)
                    }
                    dismissAllowingStateLoss()
                }
                .setNegativeButton(R.string.cancel) { _, _ -> dismissAllowingStateLoss() }
                .create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    companion object {

        private const val TAG = "CRATE_CATEGORY_DIALOG"

        fun show(fragmentManager: FragmentManager, tag: String = TAG) = CreateCategoryDialog()
                .show(fragmentManager, tag)
    }

    interface OnCreateCategory {

        fun onCreateCategory(name: String) {}
    }
}


