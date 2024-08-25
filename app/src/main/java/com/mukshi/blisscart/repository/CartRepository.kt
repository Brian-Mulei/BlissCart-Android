package com.mukshi.blisscart.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.mukshi.blisscart.App
import com.mukshi.blisscart.data.model.CartItem
import com.mukshi.blisscart.data.room.CartDao
import com.mukshi.blisscart.data.room.Database
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartRepository(private val cartDao: CartDao) {

     //   private val cartDao: CartDao = Database.cartDao()
     // Get all items in the cart
     val allCartItems:  List<CartItem> = cartDao.getAllCartItems()

    // Insert a new cart item
    suspend fun insert(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    // Update an existing cart item
    suspend fun update(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    // Delete a cart item
    suspend fun delete(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    // Clear all items in the cart
    suspend fun clearCart() {
        cartDao.clearCart()
    }
    }

