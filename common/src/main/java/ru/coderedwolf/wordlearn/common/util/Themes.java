package ru.coderedwolf.wordlearn.common.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;

public class Themes {
    
    @Nullable
    public static Drawable resolveDrawableAttr(@NonNull final Context context,
                                               @AttrRes final int attr) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return (value.resourceId != 0 ? AppCompatResources.getDrawable(context, value.resourceId) : null);
    }
    
    @IntRange(from = 0)
    @Px
    public static int resolveDimensionAttr(@NonNull final Context context,
                                           @AttrRes final int attr) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        final Resources resources = context.getResources();
        if (value.resourceId != 0) {
            return resources.getDimensionPixelSize(value.resourceId);
        }
        return (int) value.getDimension(resources.getDisplayMetrics());
    }
    
    @ColorInt
    public static int resolveColorAttr(@NonNull final Context context, @AttrRes final int attr) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        if (value.resourceId != 0) {
            return ContextCompat.getColor(context, value.resourceId);
        }
        return value.data;
    }
    
    @NonNull
    public static TypedValue resolveAttr(@NonNull final Context context, @AttrRes final int attr) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(attr, value, true);
        return value;
    }
    
    private Themes() {
        // No instances
    }
    
}
