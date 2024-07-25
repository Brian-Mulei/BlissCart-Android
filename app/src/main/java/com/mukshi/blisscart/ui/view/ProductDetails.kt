package com.mukshi.blisscart.ui.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.Product
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val product = intent.getParcelableExtra<Product>("product")
        product?.let {
            findViewById<TextView>(R.id.product_name).text = it.product_name
            findViewById<TextView>(R.id.product_price).text = "$${it.price}"
            findViewById<TextView>(R.id.product_description).text = it.description
            findViewById<TextView>(R.id.product_quantity).text = "Quantity left: ${it.quantity}"

            val productImage = findViewById<ImageView>(R.id.product_image)
            Picasso.get().load(it.image_url).into(productImage)


        }

        findViewById<Button>(R.id.add_to_cart_button).setOnClickListener {
            // Handle add to cart
        }

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }


    }
}