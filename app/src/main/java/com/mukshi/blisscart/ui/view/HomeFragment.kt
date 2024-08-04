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
import com.mukshi.blisscart.data.model.Product
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
        Product(1, 1, "Basketball", "Blue", "https://mulei-blisscart.s3.eu-north-1.amazonaws.com/4204af0c-4c72-400c-987f-f91072baf7b5_basket.jpeg", 10, 100.0),
        Product(4, 3, "Soccer Ball", "Description for product 4", "https://plus.unsplash.com/premium_photo-1675186049409-f9f8f60ebb5e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 12, 250.0),

                Product(2, 2, "Red Dress", "Ball", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=1760&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 5, 150.0),
        Product(3, 1, "Blue scarf 3", "Description for product 3", "https://plus.unsplash.com/premium_photo-1675186049409-f9f8f60ebb5e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 8, 200.0),
    )

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
