package ru.coderedwolf.wordlearn.common.extension

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * @author HaronCode. Date 21.08.2019.
 */

inline fun View.onClick(
        crossinline consumer: (View) -> Unit
) = setOnClickListener { view -> consumer(view) }


@Suppress("NOTHING_TO_INLINE")
inline fun Fragment.snack(message: String) =
        Snackbar.make(view!!, message, Snackbar.LENGTH_SHORT).show()