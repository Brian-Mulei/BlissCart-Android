package com.mukshi.blisscart.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukshi.blisscart.data.model.CartItem
import com.mukshi.blisscart.data.room.CartDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDao: CartDao ) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> get() = _cartItems

    private val _selectedQuantity = MutableLiveData<Int>()
    val selectedQuantity: LiveData<Int> get() = _selectedQuantity

    init {
        loadCartItems()
    }

      fun loadCartItems() {
        viewModelScope.launch(Dispatchers.IO) {
            _cartItems.postValue(cartDao.getAllCartItems())
        }
    }

    fun addOrUpdateCartItem(productId: Int, variationId: Int, productName: String,variationName:String,vendorName:String ,image_url:String,  quantity: Int, price: Double ,action:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingCartItem = cartDao.getCartItemByProductId(variationId)
            if (existingCartItem != null) {
                // Update the quantity of the existing item
                if(action =="REPLACE") {
                    val updatedItem =
                        existingCartItem.copy(quantity =   quantity)
                    cartDao.updateCartItem(updatedItem)
                    loadCartItems()
                }else{
                    val updatedItem =
                        existingCartItem.copy(quantity = existingCartItem.quantity + quantity)
                    cartDao.updateCartItem(updatedItem)
                    loadCartItems()

                }
            }
            else {
                // Insert a new item if it doesn't exist
                val newCartItem = CartItem(
                    productId = productId,
                    variationId = variationId,
                    variationName = variationName,
                    vendorName=vendorName,
                    productName = productName,
                    image_url = image_url,
                    quantity = quantity,
                    price = price
                )
                cartDao.insertCartItem(newCartItem)
                loadCartItems()


            }
            loadCartItems()
        }
        loadCartItems()
    }





    fun getCartItemByVariation(productId: Int ) {
        viewModelScope.launch {
            val cartItem = cartDao.getCartItemByProductId(productId)
//            cartItem?.let {
//                _selectedQuantity.value = it.quantity
//            }

            cartItem?.let {
                _selectedQuantity.value = it.quantity
            } ?: run {
                _selectedQuantity.value = 0 // Default to 0 if the item is not found
            }

        }
    }



    fun removeCartItem(productId: Int) {


        viewModelScope.launch(Dispatchers.IO) {
            val existingCartItem = cartDao.getCartItemByProductId(productId)
            if (existingCartItem != null) {
//                if (existingCartItem.quantity > 1) {
//                    // Decrease quantity
//                    val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity - 1)
//                    cartDao.updateCartItem(updatedCartItem)
//                } else


                    // Remove item if quantity is 1
                    cartDao.deleteCartItem(existingCartItem)

            }
            loadCartItems()

        }

        loadCartItems()

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