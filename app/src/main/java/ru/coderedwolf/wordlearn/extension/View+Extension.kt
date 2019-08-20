package ru.coderedwolf.wordlearn.extension

import android.view.View

/**
 * @author CodeRedWolf. Date 21.08.2019.
 */

fun View.onClick(consumer: (View) -> Unit) = setOnClickListener(consumer)