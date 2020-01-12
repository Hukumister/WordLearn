package ru.coderedwolf.wordlearn.common.domain.resource

import android.content.Context
import android.text.Spanned
import androidx.core.text.parseAsHtml
import androidx.core.text.toSpanned
import kotlin.reflect.KClass

/**
 * @author CodeRedWolf. Date 10.11.2019.
 */

typealias ArgFormatter<T> = (Context, T) -> CharSequence

object FormattedTextFormat {

    data class Config(
        val parseHtml: Boolean = true,
        val formatter: Map<KClass<*>, ArgFormatter<*>> = mapOf()
    )

    private val defaultConfig = Config()

    fun format(
        context: Context,
        formattedText: FormattedText,
        config: Config = defaultConfig
    ): CharSequence = when (formattedText) {
        is CharSequenceFormatted -> formatCharSequence(formattedText, context, config)
        is StringResFormatted -> formatStringRes(formattedText, context, config)
    }

    private fun formatCharSequence(formatted: CharSequenceFormatted, context: Context, config: Config): CharSequence {
        val formattedFormatArgs = formatted.args
            .map { arg -> formatArgumentNull(context, arg, config) }
            .toTypedArray()
        return if (formattedFormatArgs.isEmpty()) {
            formatted.value
        } else {
            String.format(formatted.value.toString(), *formattedFormatArgs)
        }
    }

    private fun formatStringRes(formatted: StringResFormatted, context: Context, config: Config): Spanned {
        val formattedFormatArgs = formatted.args
            .map { arg -> formatArgumentNull(context, arg, config) }
            .toTypedArray()
        val formattedString = context.getString(formatted.stringRes, *formattedFormatArgs)
        return if (config.parseHtml) formattedString.parseAsHtml() else formattedString.toSpanned()
    }

    private fun <T : Any?> formatArgumentNull(
        context: Context,
        argument: T,
        config: Config
    ) = (argument as? Any)?.let { notNullArgument ->
        formatArgument(context, notNullArgument, config)
    } ?: ""

    private fun <T : Any> formatArgument(
        context: Context,
        argument: T,
        config: Config
    ): CharSequence? = if (config.formatter.containsKey(argument::class)) {
        val function = config.formatter[argument::class]
        (function as ArgFormatter<T>)
        function.invoke(context, argument)
    } else {
        defaultFormatArgument(argument)
    }

    private fun <T : Any> defaultFormatArgument(
        argument: T
    ): CharSequence? = argument.toString()

}