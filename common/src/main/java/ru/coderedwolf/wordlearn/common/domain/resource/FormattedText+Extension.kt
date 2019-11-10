package ru.coderedwolf.wordlearn.common.domain.resource

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import ru.coderedwolf.wordlearn.common.domain.resource.StringResFormattedText.Companion.write

/**
 * @author CodeRedWolf. Date 10.11.2019.
 */
sealed class FormattedText : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = when (this) {
        is StringResFormattedText -> write(parcel, flags)
    }

    override fun describeContents(): Int = 0

}

@Parcelize
class StringResFormattedText(
    @get:StringRes val stringRes: Int,
    vararg val args: Any?
) : FormattedText() {

    companion object : Parceler<StringResFormattedText> {

        override fun create(parcel: Parcel): StringResFormattedText {
            parcel.readString()
            return StringResFormattedText(parcel.readInt(), parcel.readArray(Any::class.java.classLoader))
        }

        override fun StringResFormattedText.write(parcel: Parcel, flags: Int) {
            parcel.writeString(this::class.java.name)
            parcel.writeInt(stringRes)
            parcel.writeArray(args)
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringResFormattedText

        if (stringRes != other.stringRes) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stringRes
        result = 31 * result + args.contentHashCode()
        return result
    }

}

fun @receiver:StringRes Int.toStringRes(args: Any? = null) = StringResFormattedText(this, args)