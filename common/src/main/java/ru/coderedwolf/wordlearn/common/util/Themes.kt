package ru.coderedwolf.wordlearn.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.annotation.Px
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat

/**
 * @author CodeRedWolf. Date 29.08.2019.
 */
object Themes {

    fun resolveDrawableAttr(context: Context, @AttrRes attr: Int): Drawable? {
        val value = TypedValue()
        context.theme.resolveAttribute(attr, value, true)
        return if (value.resourceId != 0) AppCompatResources.getDrawable(context, value.resourceId) else null
    }

    @IntRange(from = 0)
    @Px
    fun resolveDimensionAttr(context: Context, @AttrRes attr: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(attr, value, true)
        val resources = context.resources
        return if (value.resourceId != 0) {
            resources.getDimensionPixelSize(value.resourceId)
        } else value.getDimension(resources.displayMetrics).toInt()
    }

    @ColorInt
    fun resolveColorAttr(context: Context, @AttrRes attr: Int): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(attr, value, true)
        return if (value.resourceId != 0) {
            ContextCompat.getColor(context, value.resourceId)
        } else value.data
    }

    fun resolveAttr(context: Context, @AttrRes attr: Int): TypedValue {
        val value = TypedValue()
        context.theme.resolveAttribute(attr, value, true)
        return value
    }

}