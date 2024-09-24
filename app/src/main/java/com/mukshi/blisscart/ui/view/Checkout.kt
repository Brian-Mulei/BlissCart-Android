package com.mukshi.blisscart.ui.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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
class Checkout : AppCompatActivity() {

    private lateinit var recyclerViewCartItems: RecyclerView
    private lateinit var btnCheckout: Button
    private lateinit var spinnerPaymentMethod: Spinner
    private lateinit var etPhoneNumber: EditText
    private lateinit var etCardNumber: EditText
    private lateinit var btnBack: ImageButton
    private lateinit var totalPriceTextView: TextView

    private lateinit var cartAdapter: CartItemAdapter

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(App.instance.getDatabase().cartDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_checkout)

        initializeViews()
        setupRecyclerView()
        setupPaymentMethodSelection()
        setupObservers()
        setupListeners()
    }

    private fun initializeViews() {
        recyclerViewCartItems = findViewById(R.id.recyclerViewCartItems)
        btnCheckout = findViewById(R.id.btnCheckout)
        spinnerPaymentMethod = findViewById(R.id.spinnerPaymentMethod)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etCardNumber = findViewById(R.id.etCardNumber)
        btnBack = findViewById(R.id.btnBack)
        totalPriceTextView = findViewById(R.id.total_price)
    }

    private fun setupRecyclerView() {
        recyclerViewCartItems.layoutManager = LinearLayoutManager(this)
        cartAdapter = CartItemAdapter(emptyList(),
            { cartItem ->
                cartViewModel.addOrUpdateCartItem(cartItem.productId, cartItem.variationId, cartItem.productName, cartItem.variationName, cartItem.vendorName, cartItem.image_url, 1, cartItem.price, "ADD")
            },
            { cartItem ->
                if (cartItem.quantity > 1) {
                    cartViewModel.addOrUpdateCartItem(cartItem.productId, cartItem.variationId, cartItem.productName, cartItem.variationName, cartItem.vendorName, cartItem.image_url, -1, cartItem.price, "ADD")
                } else {
                    confirmRemoveItem(cartItem)
                }
            },
            { cartItem ->
                confirmRemoveItem(cartItem)
            }
        )
        recyclerViewCartItems.adapter = cartAdapter
    }

    private fun setupPaymentMethodSelection() {
        val paymentOptions = arrayOf("Mobile Money", "Card")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, paymentOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPaymentMethod.adapter = adapter

      // spinnerPaymentMethod.setBackgroundColor(0x00000000)
        spinnerPaymentMethod.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {


                when (paymentOptions[position]) {
                    "Mobile Money" -> {
                        etPhoneNumber.visibility = View.VISIBLE
                        etCardNumber.visibility = View.GONE
                    }
                    "Card" -> {
                        etPhoneNumber.visibility = View.GONE
                        etCardNumber.visibility = View.VISIBLE
                    }
                }

                (view as TextView).setTextColor(Color.BLACK) //Change selected text color

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupObservers() {
        cartViewModel.cartItems.observe(this) { cartItems ->
            cartAdapter.updateList(cartItems)
            updateTotalPrice(cartItems)
        }
    }

    private fun setupListeners() {
        btnBack.setOnClickListener {
            finish()
        }

        btnCheckout.setOnClickListener {
            // Handle checkout logic here
            // You can capture phone number or card details based on the payment method selected
        }
    }

    private fun updateTotalPrice(cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        totalPriceTextView.text = "Total: KES ${"%.2f".format(totalPrice)}"
    }

    private fun confirmRemoveItem(cartItem: CartItem) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Remove Item")
            .setMessage("Are you sure you want to remove this item from the cart?")
            .setPositiveButton("Yes") { _, _ ->
                cartViewModel.removeCartItem(cartItem.productId)
            }
            .setNegativeButton("No", null)
            .create()
        dialog.show()
    }

}