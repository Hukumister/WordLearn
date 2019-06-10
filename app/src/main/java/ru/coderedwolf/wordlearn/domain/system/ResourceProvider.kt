package ru.coderedwolf.wordlearn.domain.system

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

/**
 * @author CodeRedWolf. Date 09.06.2019.
 */

class ResourceProvider @Inject constructor(private val context: Context) {

    fun getString(@StringRes res: Int): String = context.getString(res)

    fun getString(@StringRes res: Int, vararg args: Any): String = context.getString(res, args)
}

