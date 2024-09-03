package com.mukshi.blisscart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Int,
    val variationId: Int,
    val productName: String,

    val vendorName: String,
    val variationName: String,
    val image_url: String,

    var quantity: Int,
    val price: Double
) {


}
