package ru.haroncode.wordlearn.common.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.haroncode.wordlearn.common.R
import ru.haroncode.wordlearn.common.extension.args

class MessageDialogFragment : DialogFragment() {

    private var startTag: String by args()
    private var title: String by args()
    private var message: String by args()
    private var positiveText: String by args()
    private var negativeText: String by args()

    private val clickListener
        get() = when {
            parentFragment is OnClickListener -> parentFragment as OnClickListener
            activity is OnClickListener -> activity as OnClickListener
            else -> object : OnClickListener {}
        }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context!!).apply {
            setTitle(title)
            setMessage(message)
            setUpPositiveButton(positiveText)
            setUpNegativeButton(negativeText)
        }.create()

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        clickListener.dialogCanceled(startTag)
    }

    companion object {

        fun create(
            title: String = "",
            message: String,
            positive: String = "",
            negative: String = "",
            tag: String = "message_dialog"
        ) = MessageDialogFragment().apply {
            this.title = title
            this.startTag = tag
            this.message = message
            this.positiveText = positive
            this.negativeText = negative
        }
    }

    interface OnClickListener {
        fun dialogPositiveClicked(tag: String) {}
        fun dialogNegativeClicked(tag: String) {}
        fun dialogCanceled(tag: String) {}
    }

    private fun AlertDialog.Builder.setUpPositiveButton(positiveText: String) {
        val textButton = if (positiveText.isNotEmpty()) {
            positiveText
        } else {
            getString(R.string.action_ok)
        }
        setPositiveButton(textButton) { _, _ ->
            dismissAllowingStateLoss()
            clickListener.dialogPositiveClicked(startTag)
        }
    }

    private fun AlertDialog.Builder.setUpNegativeButton(negativeText: String) {
        if (negativeText.isNotEmpty()) {
            setNegativeButton(negativeText) { _, _ ->
                dismissAllowingStateLoss()
                clickListener.dialogNegativeClicked(startTag)
            }
        }
    }
}
