package ru.coderedwolf.wordlearn.common.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

interface ContextExtensionsHolder {

    val extensionContext: Context

    private val resources: Resources
        get() = extensionContext.resources

    @CheckResult
    fun @receiver:StringRes Int.resText(): CharSequence = extensionContext.getText(this)

    @CheckResult
    fun @receiver:StringRes Int.resString(arg: Any? = null, vararg args: Any?): String =
        if (arg == null) extensionContext.getString(this) else extensionContext.getString(this, arg, *args)

    @CheckResult
    fun @receiver:PluralsRes Int.resPlural(quantity: Int, vararg formatArgs: Any): String =
        resources.getQuantityString(this, quantity, *formatArgs)

    @CheckResult
    fun @receiver:DrawableRes Int.resDrawable(@ColorRes tintColorRes: Int = 0): Drawable? {
        val drawable = AppCompatResources.getDrawable(extensionContext, this)
        if (tintColorRes != 0 && drawable != null) {
            val wrappedDrawable = DrawableCompat.wrap(drawable)
            val color = ContextCompat.getColor(extensionContext, tintColorRes)
            DrawableCompat.setTint(wrappedDrawable, color)
            return wrappedDrawable
        }
        return drawable
    }

    @CheckResult
    @Px
    fun @receiver:DimenRes Int.dimensionPixelSizeRes(): Int = resources.getDimensionPixelSize(this)

    @CheckResult
    @ColorInt
    fun @receiver:ColorRes Int.colorRes(): Int = ContextCompat.getColor(extensionContext, this)

    @CheckResult
    fun @receiver:ColorRes Int.colorStateListRes(): ColorStateList =
        AppCompatResources.getColorStateList(extensionContext, this)!!


    @CheckResult
    fun @receiver:AttrRes Int.dimensionPixelAttr(context: Context = extensionContext): Int =
        Themes.resolveDimensionAttr(context, this)

    @CheckResult
    fun @receiver:AttrRes Int.colorAttr(context: Context = extensionContext): Int =
        Themes.resolveColorAttr(context, this)

    @CheckResult
    fun @receiver:AttrRes Int.drawableAttr(context: Context = extensionContext): Drawable? =
        Themes.resolveDrawableAttr(context, this)

}