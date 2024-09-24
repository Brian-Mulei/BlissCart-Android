package com.mukshi.blisscart.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
import com.mukshi.blisscart.ui.viewmodel.ProductViewModel
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
    private lateinit var productViewModel: ProductViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productViewModel = ViewModelProvider(
            this,
         )[ProductViewModel::class.java]

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Initial data load
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]


        val searchBar= view.findViewById<EditText>(R.id.search_bar)
        val searchButton= view.findViewById<Button>(R.id.search_button)

        productViewModel.getAll(isInitialLoad = true)

        productAdapter = ProductAdapter(emptyList(), { product ->
            val intent = Intent(context, ProductDetails::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        },cartViewModel  )

        productViewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter.updateList(products)
        }

        productViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    // Load the next page when nearing the end of the list
               //     productViewModel.loadNextPage()

                    productViewModel.getAll(isInitialLoad = false)

                }
            }
        })


        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = productAdapter




        searchButton.setOnClickListener {
            val query = searchBar.text.toString()
            productAdapter.filter(query)
        }
    }
}
