package ru.coderedwolf.wordlearn.common.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.layout_input_text_dialog.*
import ru.coderedwolf.wordlearn.common.R
import ru.coderedwolf.wordlearn.common.extension.args

/**
 * @author HaronCode. Date 12.06.2019.
 */
class InputTextDialogFragment : DialogFragment() {

    private var startTag: String by args()
    private var title: String by args()
    private var positiveText: String by args()
    private var negativeText: String by args()

    private val clickListener
        get() = when {
            parentFragment is OnClickListener -> parentFragment as OnClickListener
            activity is OnClickListener -> activity as OnClickListener
            else -> object : OnClickListener {}
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        @SuppressLint("InflateParams")
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_input_text_dialog, null)

        return AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.create_category_title))
            setView(view)
            setUpPositiveButton(positiveText)
            setUpNegativeButton(negativeText)
        }.create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        clickListener.inputDialogCanceled(startTag)
    }

    override fun onResume() {
        super.onResume()
        requireDialog().inputText.requestFocus()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    }

    companion object {

        fun create(
            title: String = "",
            positive: String = "",
            negative: String = "",
            tag: String = "message_dialog"
        ) = InputTextDialogFragment().apply {
            this.title = title
            this.startTag = tag
            this.positiveText = positive
            this.negativeText = negative
        }
    }

    interface OnClickListener {
        fun inputDialogTextAccepted(tag: String, text: String) {}
        fun inputDialogCanceled(tag: String) {}
    }

    private fun AlertDialog.Builder.setUpPositiveButton(positiveText: String) {
        val textButton = if (positiveText.isNotEmpty()) {
            positiveText
        } else {
            getString(R.string.action_ok)
        }
        setPositiveButton(textButton) { _, _ ->
            val string = requireDialog().inputText.text.toString()
            if (string.isNotEmpty()) {
                clickListener.inputDialogTextAccepted(startTag, string)
            }
            dismissAllowingStateLoss()
        }
    }

    private fun AlertDialog.Builder.setUpNegativeButton(negativeText: String) {
        if (negativeText.isNotEmpty()) {
            setNegativeButton(negativeText) { _, _ ->
                dismissAllowingStateLoss()
                clickListener.inputDialogCanceled(startTag)
            }
        }
    }
}
