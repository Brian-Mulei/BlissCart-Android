package com.mukshi.blisscart.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukshi.blisscart.App
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.CartItem
import com.mukshi.blisscart.ui.adapter.CartItemAdapter
import com.mukshi.blisscart.ui.viewmodel.CartViewModel
import com.mukshi.blisscart.ui.viewmodel.CartViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(App.instance.getDatabase().cartDao())
    }

    private lateinit var cartAdapter: CartItemAdapter
    private lateinit var totalPriceTextView: TextView
    private lateinit var goToCheckoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartViewModel.loadCartItems()

        // Initialize views
        totalPriceTextView = view.findViewById(R.id.total_price)
        goToCheckoutButton = view.findViewById(R.id.go_to_checkout_button)

        // Initialize RecyclerView and Adapter
        val recyclerView: RecyclerView = view.findViewById(R.id.cart_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize cartAdapter
        cartAdapter = CartItemAdapter(emptyList(),
            { cartItem ->
                // Handle incrementing quantity
                cartViewModel.addOrUpdateCartItem(cartItem.productId, cartItem.variationId,cartItem.productName, cartItem.variationName,cartItem.vendorName, cartItem.image_url, 1, cartItem.price,"ADD")
            },
            { cartItem ->
                // Handle decrementing quantity
                if (cartItem.quantity > 1) {
                    cartViewModel.addOrUpdateCartItem(cartItem.productId, cartItem.variationId,cartItem.productName, cartItem.variationName,cartItem.vendorName, cartItem.image_url, -1, cartItem.price,"ADD")
                } else {
                    // Show a confirmation dialog before removing the item
                    confirmRemoveItem(cartItem)
                }
            },
            { cartItem ->
                // Handle removing the item
                confirmRemoveItem(cartItem)
            }
        )

        recyclerView.adapter = cartAdapter

        // Observe cart items from ViewModel
        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            Log.d("CART ITEMS Details", "Response: ${cartItems}")

            cartAdapter.updateList(cartItems)
            updateTotalPrice(cartItems)
        }

        // Set checkout button click listener
        goToCheckoutButton.setOnClickListener {
            // Handle checkout logic, e.g., navigate to checkout screen

            val intent = Intent(requireContext(), Checkout::class.java)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        cartViewModel.loadCartItems()
    }
    private fun updateTotalPrice(cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        totalPriceTextView.text = "Total: KES ${"%.2f".format(totalPrice)}"
    }

    private fun confirmRemoveItem(cartItem: CartItem) {
        // Show a confirmation dialog to the user before removing the item
        // You can use AlertDialog.Builder or any custom dialog implementation here
        val dialog = android.app.AlertDialog.Builder(requireContext())
            .setTitle("Remove Item")
            .setMessage("Are you sure you want to remove this item from the cart?")
            .setPositiveButton("Yes") { _, _ ->
                cartViewModel.removeCartItem(cartItem.variationId)
            }
            .setNegativeButton("No", null)
            .create()
        dialog.show()
    }
}
