package ru.coderedwolf.wordlearn.common.extension

import android.view.View

/**
 * @author CodeRedWolf. Date 21.08.2019.
 */

inline fun View.onClick(
    crossinline consumer: (View) -> Unit
) = setOnClickListener { view -> consumer(view) }