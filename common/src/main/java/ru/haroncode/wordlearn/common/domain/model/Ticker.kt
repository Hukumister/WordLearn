package ru.haroncode.wordlearn.common.domain.model

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticker(
    val name: String,
    @ColorInt val color: Int
) : Parcelable
