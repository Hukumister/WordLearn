package ru.haroncode.wordlearn.common.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * @author HaronCode.
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)