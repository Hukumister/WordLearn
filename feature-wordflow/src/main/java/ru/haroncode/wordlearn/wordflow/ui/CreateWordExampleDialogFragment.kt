package ru.haroncode.wordlearn.wordflow.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.layout_create_word_example_dialog.*
import ru.haroncode.wordlearn.word.model.WordExample
import ru.haroncode.wordlearn.wordflow.R

/**
 * @author HaronCode. Date 12.06.2019.
 */
class CreateWordExampleDialogFragment : DialogFragment() {

    private val listener
        get() = when {
            parentFragment is OnCreateExampleListener -> parentFragment as OnCreateExampleListener
            activity is OnCreateExampleListener -> activity as OnCreateExampleListener
            else -> object : OnCreateExampleListener {}
        }

    override fun onResume() {
        super.onResume()
        check(showsDialog) { "This fragment should be used only as dialog" }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.dialog_create_word_example_title)
            setView(R.layout.layout_create_word_example_dialog)
            setNegativeButton(R.string.action_cancel) { _, _ -> dismissAllowingStateLoss() }
            setPositiveButton(R.string.action_ok) { _, _ ->
                val dialog = requireDialog()

                val exampleText = dialog.exampleText.text.toString()
                val translation = dialog.translation.text.toString()

                if (exampleText.isNotEmpty() && translation.isNotEmpty()) {
                    listener.onCreateWordExample(
                            WordExample(example = exampleText, translation = translation)
                    )
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

        const val TAG = "CreateWordExampleDialogFragment"

        fun instance(): CreateWordExampleDialogFragment = CreateWordExampleDialogFragment()

    }

    interface OnCreateExampleListener {

        fun onCreateWordExample(wordExample: WordExample) {}

    }
}