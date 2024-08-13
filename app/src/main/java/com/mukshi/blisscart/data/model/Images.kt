package com.mukshi.blisscart.data.model

import android.os.Parcel
import android.os.Parcelable

data class Images(
    val id: Int,
    val image_url: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(image_url)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Images> {
        override fun createFromParcel(parcel: Parcel): Images {
            return Images(parcel)
        }

        override fun newArray(size: Int): Array<Images?> {
            return arrayOfNulls(size)
        }
    }
}
