package ru.coderedwolf.wordlearn.wordflow.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.layout_create_word_example_dialog.*
import ru.coderedwolf.wordlearn.wordflow.R

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
        if (!showsDialog) {
            throw IllegalStateException("This fragment should be used only as dialog")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.dialog_create_word_example_title)
            setView(R.layout.layout_create_word_example_dialog)
            setNegativeButton(R.string.cancel) { _, _ -> dismissAllowingStateLoss() }
            setPositiveButton(R.string.ok) { _, _ ->
                val dialog = requireDialog()

                val exampleText = dialog.exampleText.text.toString()
                val translation = dialog.translation.text.toString()

                if (exampleText.isNotEmpty() && translation.isNotEmpty()) {
                    listener.onCreateWordExample(exampleText, translation)
                }
                dismissAllowingStateLoss()
            }
        }.create()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_VISIBLE)
    }

    companion object {

        fun instance(): CreateWordExampleDialogFragment = CreateWordExampleDialogFragment()

    }

    interface OnClickListener {

        fun onCreateWordExample(text: String, translation: String) {}

    }
}