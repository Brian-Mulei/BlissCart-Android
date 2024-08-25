package com.mukshi.blisscart.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.Product
import com.mukshi.blisscart.ui.viewmodel.CartViewModel
import com.squareup.picasso.Picasso

class   ProductAdapter(
    private var products: List<Product>,
    private val onClick: (Product) -> Unit,
    private val cartViewModel: CartViewModel,
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable{

    private var  filteredProducts :List<Product> = products
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card,parent,false)
        return ProductViewHolder(view)
        }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val product =filteredProducts[position]

        holder.bind(product)
    }

    override fun getItemCount(): Int =filteredProducts.size

    fun updateList(newProducts: List<Product>) {
        products = newProducts
        filteredProducts = newProducts
        notifyDataSetChanged()
    }

      fun filter(query:String): List<Product> {

val  lowerCaseQuery = query.lowercase()

          filteredProducts = if(lowerCaseQuery.isEmpty()){
              products
          }else{
              products.filter {
                  it.name.lowercase().contains(lowerCaseQuery) ||
                          it.description.lowercase().contains(lowerCaseQuery)
              }
          }
           return filteredProducts
      }
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productName: TextView = itemView.findViewById(R.id.product_name)
         private val productImage: ImageView = itemView.findViewById(R.id.product_image)


        fun bind(product: Product) {
            Log.d("product_name", "Response: ${product}")

            productName.text = product.name

            Glide.with(itemView.context)
                .load( product.images.takeIf { it?.isNotEmpty() == true }?.get(0)?.image_url
                    ?: R.drawable.ic_placeholder )
                .placeholder(R.drawable.ic_placeholder) // Placeholder image while loading
                .error(R.drawable.ic_placeholder) // Error image if loading fails
                .into(productImage)



            itemView.setOnClickListener {
                onClick(product)
            }
//            removeButton.visibility = View.GONE


//            cartViewModel.cartItems.observeForever { cartItems ->
//                val cartItem = cartItems.find { it.productId == product.id }
//                if (cartItem != null) {
//                    quantityTextView.text = "Qty: ${cartItem.quantity}"
//                    removeButton.visibility = View.VISIBLE
//
//                } else {
//                    quantityTextView.text = "Qty: 0"
//                    removeButton.visibility = View.GONE
//
//                }
//            }
        }

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


}


