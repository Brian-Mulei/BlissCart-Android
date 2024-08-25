package com.mukshi.blisscart.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(

    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Int,
    val productName: String,
     var quantity: Int,
    val price: Double
) {


}
