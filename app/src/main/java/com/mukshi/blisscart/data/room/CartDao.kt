package com.mukshi.blisscart.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mukshi.blisscart.data.model.CartItem
import dagger.Provides
import javax.inject.Singleton

@Dao
 interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
   suspend   fun getCartItemByProductId(productId: kotlin.Int): CartItem?
}
