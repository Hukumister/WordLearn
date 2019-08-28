package ru.coderedwolf.wordlearn.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.coderedwolf.wordlearn.common.R

class ProgressDialog : DialogFragment() {
    companion object {
        const val TAG = "progress_dialog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_progress, container, false)
}