package com.mukshi.blisscart.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.Product
import com.squareup.picasso.Picasso
import java.util.Locale

class ProductAdapter(private val products:List<Product> , private val onClick:(Product)->Unit) :
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
        private val quickAddButton: ImageButton = itemView.findViewById(R.id.quick_add_button)



        fun bind(product: Product) {

            productName.text = product.product_name
            productPrice.text = product.price.toString()
//            productImage.setImageResource(product.image_url)
            Picasso.get().load(product.image_url).into(productImage)

            quickAddButton.setOnClickListener{}

            itemView.setOnClickListener {
                onClick(product)
            }


        }

    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }


}


