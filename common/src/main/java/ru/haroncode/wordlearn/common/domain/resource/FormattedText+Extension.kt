package ru.haroncode.wordlearn.common.domain.resource

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import ru.haroncode.wordlearn.common.domain.resource.CharSequenceFormatted.Companion.write
import ru.haroncode.wordlearn.common.domain.resource.StringResFormatted.Companion.write

/**
 * @author HaronCode.
 */
sealed class FormattedText : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) = when (this) {
        is CharSequenceFormatted -> write(parcel, flags)
        is StringResFormatted -> write(parcel, flags)
    }

    override fun describeContents(): Int = 0
}

@Parcelize
class StringResFormatted(
    @get:StringRes val stringRes: Int,
    vararg val args: Any?
) : FormattedText() {

    companion object : Parceler<StringResFormatted> {

        override fun create(parcel: Parcel): StringResFormatted {
            parcel.readString()
            return StringResFormatted(parcel.readInt(), parcel.readArray(Any::class.java.classLoader))
        }

        override fun StringResFormatted.write(parcel: Parcel, flags: Int) {
            parcel.writeString(this::class.java.name)
            parcel.writeInt(stringRes)
            parcel.writeArray(args)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StringResFormatted

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

@Parcelize
class CharSequenceFormatted(
    val value: CharSequence,
    vararg val args: Any?
) : FormattedText() {

    companion object : Parceler<CharSequenceFormatted> {

        override fun create(parcel: Parcel): CharSequenceFormatted {
            parcel.readString()
            return CharSequenceFormatted(
                TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel),
                parcel.readArray(Any::class.java.classLoader)
            )
        }

        override fun CharSequenceFormatted.write(parcel: Parcel, flags: Int) {
            parcel.writeString(this::class.java.name)
            TextUtils.writeToParcel(value, parcel, flags)
            parcel.writeArray(args)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharSequenceFormatted

        if (value != other.value) return false
        if (!args.contentEquals(other.args)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + args.contentHashCode()
        return result
    }
}

fun @receiver:StringRes Int.toFormatted(args: Any? = null) = StringResFormatted(this, args)

fun CharSequence.toFormatted(args: Any? = null) = CharSequenceFormatted(this, args)
