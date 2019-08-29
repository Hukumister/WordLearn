package ru.coderedwolf.wordlearn.common.util

import android.content.ContentResolver
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat

interface HasContextExtensions {

    fun getContextForExtensions(): Context

    private fun getResources(): Resources = getContextForExtensions().resources

    @CheckResult
    fun @receiver:StringRes Int.textRes(): CharSequence = getContextForExtensions().getText(this)

    @CheckResult
    fun @receiver:StringRes Int.stringRes(arg: Any? = null, vararg args: Any?): String =
            if (arg == null) getContextForExtensions().getString(this) else getContextForExtensions().getString(this, arg, *args)

    @CheckResult
    fun @receiver:PluralsRes Int.pluralRes(quantity: Int, vararg formatArgs: Any): String =
            getResources().getQuantityString(this, quantity, *formatArgs)

    @CheckResult
    fun @receiver:DrawableRes Int.drawableRes(@ColorRes tintColorRes: Int = 0): Drawable? {
        val drawable = AppCompatResources.getDrawable(getContextForExtensions(), this)
        if (tintColorRes != 0 && drawable != null) {
            val wrappedDrawable = DrawableCompat.wrap(drawable)
            val color = ContextCompat.getColor(getContextForExtensions(), tintColorRes)
            DrawableCompat.setTint(wrappedDrawable, color)
            return wrappedDrawable
        }
        return drawable
    }

    @CheckResult
    @Px
    fun @receiver:DimenRes Int.dimensionPixelSizeRes(): Int = getResources().getDimensionPixelSize(this)

    @CheckResult
    @ColorInt
    fun @receiver:ColorRes Int.colorRes(): Int = ContextCompat.getColor(getContextForExtensions(), this)

    @CheckResult
    fun @receiver:ColorRes Int.colorStateListRes(): ColorStateList = AppCompatResources.getColorStateList(getContextForExtensions(), this)!!

    @CheckResult
    fun @receiver:FontRes Int.fontRes(): Typeface? = ResourcesCompat.getFont(getContextForExtensions(), this)

    @CheckResult
    fun @receiver:AnyRes Int.toResourceUri(): Uri = Uri.Builder()
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(getResources().getResourcePackageName(this))
            .appendPath(getResources().getResourceTypeName(this))
            .appendPath(getResources().getResourceEntryName(this))
            .build()

    @CheckResult
    fun @receiver:AttrRes Int.dimensionPixelAttr(context: Context = getContextForExtensions()): Int = Themes.resolveDimensionAttr(context, this)

    @CheckResult
    fun @receiver:AttrRes Int.colorAttr(context: Context = getContextForExtensions()): Int = Themes.resolveColorAttr(context, this)

    @CheckResult
    fun @receiver:AttrRes Int.drawableAttr(context: Context = getContextForExtensions()): Drawable? = Themes.resolveDrawableAttr(context, this)

}