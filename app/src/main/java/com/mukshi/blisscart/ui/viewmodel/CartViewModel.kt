package com.mukshi.blisscart.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukshi.blisscart.data.model.CartItem
import com.mukshi.blisscart.data.room.CartDao
import dagger.Binds
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDao: CartDao) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _cartItems.postValue(cartDao.getAllCartItems())
        }
    }

    fun addOrUpdateCartItem(productId: Long, productName: String, quantity: Int, price: Double,productImage:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingCartItem = cartDao.getCartItemByProductId(productId)
            if (existingCartItem != null) {
                // Update the quantity of the existing item
                val updatedItem =
                    existingCartItem.copy(quantity = existingCartItem.quantity + quantity)
                cartDao.updateCartItem(updatedItem)
            } else {
                // Insert a new item if it doesn't exist
                val newCartItem = CartItem(
                    productId = productId,
                    productName = productName,
                    productImage = productImage,
                    quantity = quantity,
                    price = price
                )
                cartDao.insertCartItem(newCartItem)
            }
            loadCartItems()
        }
    }

    fun removeCartItem(productId: Long) {
//        viewModelScope.launch(Dispatchers.IO) {
//            cartDao.deleteCartItem(cartItem)
//            loadCartItems()
//        }

        viewModelScope.launch(Dispatchers.IO) {
            val existingCartItem = cartDao.getCartItemByProductId(productId)
            if (existingCartItem != null) {
                if (existingCartItem.quantity > 1) {
                    // Decrease quantity
                    val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity - 1)
                    cartDao.updateCartItem(updatedCartItem)
                } else {
                    // Remove item if quantity is 1
                    cartDao.deleteCartItem(existingCartItem)
                }
            }
        }


    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.clearCart()
            loadCartItems()
        }
    }

    fun updateCartItem(cartItem: CartItem) {
        viewModelScope.launch(Dispatchers.IO) {
            cartDao.updateCartItem(cartItem)
            loadCartItems()
        }
    }
}