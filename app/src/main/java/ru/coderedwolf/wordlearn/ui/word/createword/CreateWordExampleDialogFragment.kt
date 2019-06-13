package ru.coderedwolf.wordlearn.ui.word.createword

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.layout_create_word_example_dialog.*
import ru.coderedwolf.wordlearn.R

/**
 * @author CodeRedWolf. Date 12.06.2019.
 */
class CreateWordExampleDialogFragment : DialogFragment() {

    private val listener
        get() = when {
            parentFragment is OnClickListener -> parentFragment as OnClickListener
            activity is OnClickListener -> activity as OnClickListener
            else -> object : OnClickListener {}
        }

    override fun onResume() {
        super.onResume()
        requireDialog().exampleText.requestFocus()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        @SuppressLint("InflateParams")
        val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.layout_create_word_example_dialog, null)

        return AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.create_word_example))
            setView(view)
            setNegativeButton(R.string.cancel) { _, _ -> dismissAllowingStateLoss() }
            setPositiveButton(R.string.ok) { _, _ ->
                val exampleText = requireDialog().exampleText.text.toString()
                val translation = requireDialog().translation.text.toString()
                if (exampleText.isNotEmpty() && translation.isNotEmpty()) {
                    listener.onCreateWordExample(exampleText, translation)
                }
                dismissAllowingStateLoss()
            }
        }.create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    companion object {

        private const val DEFAULT_TAG = "create_example_dialog"

        fun show(
                fragmentManager: FragmentManager,
                tag: String = DEFAULT_TAG
        ) = CreateWordExampleDialogFragment().show(fragmentManager, tag)
    }

    interface OnClickListener {
        fun onCreateWordExample(text: String, translation: String) {}
    }
}