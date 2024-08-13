package com.mukshi.blisscart.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.Images
import com.mukshi.blisscart.data.model.Product
import com.mukshi.blisscart.data.model.Variation
import com.mukshi.blisscart.ui.adapter.ProductAdapter
import com.mukshi.blisscart.ui.viewmodel.CartViewModel
import com.mukshi.blisscart.ui.viewmodel.CartViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint

class HomeFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    private lateinit var cartViewModel: CartViewModel

    val dummyProducts = listOf(
        Product(
            502,
          1,
            1,
             "Supreme T-shirt",
             "A high-quality t-shirt with supreme branding.",
           listOf(

              Images (1,                "https://mulei-blisscart.s3.eu-north-1.amazonaws.com/45494f5a-3f25-4882-be5d-b4b49ce9f950_Basketball.png" ,

               ),
               Images (145,                "https://mulei-blisscart.s3.eu-north-1.amazonaws.com/e9eec5b0-0d12-4f70-8cff-8cef77b48038_basket.jpeg" ,

                   )  ,     Images (1945,                "https://mulei-blisscart.s3.eu-north-1.amazonaws.com/e9eec5b0-0d12-4f70-8cff-8cef77b48038_basket.jpeg" ,

               ) ,      Images (1145,                "https://mulei-blisscart.s3.eu-north-1.amazonaws.com/e9eec5b0-0d12-4f70-8cff-8cef77b48038_basket.jpeg" ,

            )   ,    Images (1345,                "https://mulei-blisscart.s3.eu-north-1.amazonaws.com/e9eec5b0-0d12-4f70-8cff-8cef77b48038_basket.jpeg" ,

        )

            ), // Replace with actual image URLs
           listOf(
                Variation(
                    id = 552,
                    productId = 502,
                    variationDescription = "Black on Red",
                    price = 90.0,
                    quantity = 100
                ),
                Variation(
                    id = 553,
                    productId = 502,
                    variationDescription = "White on Red",
                    price = 190.0,
                    quantity = 100
                )

            )
           ))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]


        val searchBar= view.findViewById<EditText>(R.id.search_bar)
        val searchButton= view.findViewById<Button>(R.id.search_button)

        productAdapter = ProductAdapter(dummyProducts, { product ->
            val intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        },cartViewModel  )
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        recyclerView.adapter = productAdapter

        searchButton.setOnClickListener {
            val query = searchBar.text.toString()
            productAdapter.filter(query)
        }
    }
}
