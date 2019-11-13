package ru.coderedwolf.wordlearn.common.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * @author CodeRedWolf. Date 26.10.2019.
 */

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)