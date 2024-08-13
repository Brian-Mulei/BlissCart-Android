package com.mukshi.blisscart.data.model

import android.os.Parcel
import android.os.Parcelable


// Variation.kt
data class Variation(
    val id: Int,
    val productId: Int,
    val variationDescription: String,
    val price: Double,
    val quantity: Int
)  : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(productId)
        parcel.writeString(variationDescription)
        parcel.writeDouble(price)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Variation> {
        override fun createFromParcel(parcel: Parcel): Variation {
            return Variation(parcel)
        }

        override fun newArray(size: Int): Array<Variation?> {
            return arrayOfNulls(size)
        }
    }
}

