package ru.coderedwolf.wordlearn.common.domain.resource

import android.content.Context

/**
 * @author CodeRedWolf. Date 10.11.2019.
 */
object FormattedTextFormat {

    fun format(
        context: Context,
        formattedText: FormattedText
    ): CharSequence = when (formattedText) {
        is StringResFormattedText -> context.getString(formattedText.stringRes, formattedText.args)
    }

}