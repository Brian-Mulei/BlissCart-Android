package com.mukshi.blisscart.ui.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mukshi.blisscart.App
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.CartItem
import com.mukshi.blisscart.data.model.Images
import com.mukshi.blisscart.data.model.Product
import com.mukshi.blisscart.data.model.Variation
import com.mukshi.blisscart.data.room.CartDao
import com.mukshi.blisscart.data.room.Database
import com.mukshi.blisscart.ui.adapter.ProductImagesAdapter
import com.mukshi.blisscart.ui.adapter.VariationsAdapter
import com.mukshi.blisscart.ui.viewmodel.CartViewModel
import com.mukshi.blisscart.ui.viewmodel.CartViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.Context
import com.mukshi.blisscart.ui.adapter.CartItemAdapter

@AndroidEntryPoint
class ProductDetails : AppCompatActivity() {

   // private lateinit var cartViewModel: CartViewModel
   private lateinit var cartDao: CartDao

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var productNameTextView: TextView
    private lateinit var productDescriptionTextView: TextView
    private lateinit var variationsRecyclerView: RecyclerView
    private lateinit var priceTextView: TextView
    private lateinit var quantityTextView: TextView
    private lateinit var decreaseQuantityButton: Button
    private lateinit var increaseQuantityButton: Button
    private lateinit var addToCartButton: Button
    private lateinit var cartAdapter: CartItemAdapter


    private var currentQuantity = 0
    private var selectedVariation: Variation? = null

    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(App.instance.getDatabase().cartDao())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
     //   val cartDao = Database.getInstance(requireContext()).cartDao()

//        val cartDao = Database.getInstance(applicationContext).cartDao()
//
//        // val cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
//        val factory = CartViewModelFactory(cartDao)
//        cartViewModel = ViewModelProvider(this, factory).get(CartViewModel::class.java)

        val product = intent.getParcelableExtra<Product>("product")

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        productNameTextView = findViewById(R.id.productNameTextView)
        productDescriptionTextView = findViewById(R.id.productDescriptionTextView)
        variationsRecyclerView = findViewById(R.id.variationsRecyclerView)
        priceTextView = findViewById(R.id.priceTextView)
        quantityTextView = findViewById(R.id.quantityTextView)
        decreaseQuantityButton = findViewById(R.id.decreaseQuantityButton)
        increaseQuantityButton = findViewById(R.id.increaseQuantityButton)
        addToCartButton = findViewById(R.id.addToCartButton)


        product?.let {


            productNameTextView.text = it.name
            viewPager = findViewById(R.id.viewPager)

            //findViewById<TextView>(R.id.product_price).text = "$${it.price}"
            productDescriptionTextView.text = it.description
        //    findViewById<TextView>(R.id.product_quantity).text = "Quantity left: ${it.quantity}"
           findViewById<ViewPager>(R.id.viewPager)
            tabLayout = findViewById(R.id.tabLayout)
//            val productImage = findViewById<ImageView>(R.id.product_image)
//            Picasso.get().load(it.image_url).into(productImage)

          //  product.images?.let { it1 -> setupImageCarousel(it1) }

            // Setup variations
        //    product.variations?.let { it1 -> setupVariations(it1) }
            setupImageCarousel(it.images ?: emptyList())
            setupVariations(it.variations ?: emptyList())


//                     val cartItem =cartViewModel.getCartItem(it.id)!!
//                Log.d("CART ITEM sss", "Response: $cartItem")
//                currentQuantity = cartItem.quantity ?: 0
//                quantityTextView.text = currentQuantity.toString()


            cartViewModel.selectedQuantity.observe(this) { quantity ->

                currentQuantity =quantity
                quantityTextView.text = currentQuantity.toString()

             }

            decreaseQuantityButton.setOnClickListener {
                if (currentQuantity > 1) {
                    currentQuantity--
                    quantityTextView.text = currentQuantity.toString()
                }
            }
            increaseQuantityButton.setOnClickListener {
                currentQuantity++
                quantityTextView.text = currentQuantity.toString()
            }


            addToCartButton.setOnClickListener {
                if (selectedVariation == null) {

                    Toast.makeText(applicationContext, "Select a variant", Toast.LENGTH_SHORT).show()

                }else
                {
                selectedVariation?.let { variation ->
                    cartViewModel.addOrUpdateCartItem(
                        variation.id,
                        variation.variationDescription,
                        currentQuantity,
                        variation.price,
                        "REPLACE"
                    )
                      Toast.makeText(applicationContext, "Cart updated", Toast.LENGTH_SHORT).show()
                }


            }

            }




        }

//        findViewById<Button>(R.id.addToCartButton).setOnClickListener {
//            // Handle add to cart
//        }

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }


    }

    private fun setupImageCarousel(images: List<Images>) {
        val imagesAdapter = ProductImagesAdapter(images)
        viewPager.adapter = imagesAdapter

        // Setup TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }

    private fun setupVariations(variations: List<Variation>) {

      //  Log.d("Product variation", "Response: ${variations}")

        variationsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val variationsAdapter = VariationsAdapter(variations) { selectedVariation ->
            cartViewModel.getCartItemByVariation(selectedVariation.id)

            this.selectedVariation = selectedVariation
            priceTextView.text = selectedVariation.price.toString()
        }
        variationsRecyclerView.adapter = variationsAdapter
    }
}