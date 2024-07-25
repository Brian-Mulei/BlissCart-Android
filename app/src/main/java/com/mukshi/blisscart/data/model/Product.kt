package com.mukshi.blisscart.data.model

import android.os.Parcelable
import androidx.room.util.TableInfo.*
import kotlinx.android.parcel.Parcelize

@Parcelize
 data class Product
  (val id:Long,
   val category_id:Long,
   val product_name: String,
   val description: String,
   val image_url: String,
   val quantity: Int,
   val price: Double):Parcelable
