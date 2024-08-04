package com.mukshi.blisscart.ui.adapter

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
    private val products: List<Product>,
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

      fun filter(query:String): List<Product> {

val  lowerCaseQuery = query.lowercase()

          filteredProducts = if(lowerCaseQuery.isEmpty()){
              products
          }else{
              products.filter {
                  it.product_name.lowercase().contains(lowerCaseQuery) ||
                          it.description.lowercase().contains(lowerCaseQuery)
              }
          }
           return filteredProducts
      }
    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)
        private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val quickAddButton: Button = itemView.findViewById(R.id.quick_add_button)

       // private val addButton: Button = itemView.findViewById(R.id.add_button)
        private val removeButton: Button = itemView.findViewById(R.id.remove_button)
        private val quantityTextView: TextView = itemView.findViewById(R.id.quantity_text_view)


        fun bind(product: Product) {

            productName.text = product.product_name
            productPrice.text = product.price.toString()
//            productImage.setImageResource(product.image_url)
            Picasso.get().load(product.image_url).into(productImage)

            Glide.with(itemView.context)
                .load(product.image_url)
                .placeholder(R.drawable.ic_launcher_background) // Placeholder image while loading
                .error(R.drawable.ic_launcher_background) // Error image if loading fails
                .into(productImage)

             quickAddButton.setOnClickListener{

                cartViewModel.addOrUpdateCartItem(product.id, product.product_name,1,product.price,product.image_url)
             }

            removeButton.setOnClickListener {
                cartViewModel.removeCartItem(product.id)

            }

            itemView.setOnClickListener {
                onClick(product)
            }
            removeButton.visibility = View.GONE


            cartViewModel.cartItems.observeForever { cartItems ->
                val cartItem = cartItems.find { it.productId == product.id }
                if (cartItem != null) {
                    quantityTextView.text = "Qty: ${cartItem.quantity}"
                    removeButton.visibility = View.VISIBLE

                } else {
                    quantityTextView.text = "Qty: 0"
                    removeButton.visibility = View.GONE

                }
            }
        }

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


}


