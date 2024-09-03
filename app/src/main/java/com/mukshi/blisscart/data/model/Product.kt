package com.mukshi.blisscart.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parceler

@Parcelize
 data class Product(
 val id:Long,
 val categoryId:Long,
 val vendorId:Long,
 val name: String,
 val vendorName: String,
 val description: String,

 val images:  @RawValue List<Images>?,

 val variations: @RawValue List<Variation>?
) : Parcelable {
 constructor(parcel: Parcel) : this(
  parcel.readLong(),
  parcel.readLong(),
  parcel.readLong(),
     parcel.readString().toString(),
  parcel.readString().toString(),
  parcel.readString().toString(),

  parcel.createTypedArrayList(Images.CREATOR),
  parcel.createTypedArrayList(Variation.CREATOR)
 )

 override fun describeContents(): Int = 0

 companion object : Parceler<Product> {

  override fun Product.write(parcel: Parcel, flags: Int) {
   parcel.writeLong(id)
   parcel.writeLong(vendorId)
   parcel.writeLong(categoryId)
   parcel.writeString(name)
   parcel.writeString(vendorName)
   parcel.writeString(description)

   parcel.writeTypedList(images)
   parcel.writeTypedList(variations)
  }

  override fun create(parcel: Parcel): Product {
   return Product(parcel)
  }
 }
}
