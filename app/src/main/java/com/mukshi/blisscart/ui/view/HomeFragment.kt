package com.mukshi.blisscart.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.Product
import com.mukshi.blisscart.ui.adapter.ProductAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var productAdapter: ProductAdapter
    val dummyProducts = listOf(
        Product(1, 1, "Product 1", "Description for product 1", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=1760&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 10, 100.0),
        Product(4, 3, "Product 4", "Description for product 4", "https://plus.unsplash.com/premium_photo-1675186049409-f9f8f60ebb5e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 12, 250.0),

                Product(2, 2, "Product 2", "Description for product 2", "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=1760&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 5, 150.0),
        Product(3, 1, "Product 3", "Description for product 3", "https://plus.unsplash.com/premium_photo-1675186049409-f9f8f60ebb5e?q=80&w=1887&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 8, 200.0),
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
        productAdapter = ProductAdapter(dummyProducts) { product ->
            val intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = productAdapter
    }
}