package com.mukshi.blisscart.ui.adapter

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.CartItem
import com.squareup.picasso.Picasso
import java.util.Collections.addAll

class CartItemAdapter(
    val cartItems: MutableList<CartItem>
) : RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }
    fun updateCartItems(newCartItems: MutableList<CartItem>) {

        cartItems.clear()
        cartItems.addAll(newCartItems)
//        with(cartItems){
//            clear()
//            addAll(newCartItems)
//        }
      //  cartItems.clear() // Remove all existing items
      //  cartItems.addAll(newCartItems) // Add new items
        notifyDataSetChanged() // Notify RecyclerView of data changes
    }

    fun getCartItemAtPosition(position: Int): CartItem = cartItems[position]


    override fun getItemCount(): Int = cartItems.size


//    fun setCartItems(newCartItems: List<CartItem>) {
//        val diffCallback = CartItemDiffCallback(cartItems, newCartItems)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        cartItems = newCartItems
//        diffResult.dispatchUpdatesTo(this)
//    }

    inner class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productName: TextView = itemView.findViewById(R.id.product_name)
        private val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        private val productPrice: TextView = itemView.findViewById(R.id.product_price)


         private val productImage: ImageView = itemView.findViewById(R.id.product_image)
        private val productTotal: TextView = itemView.findViewById(R.id.product_total)

        fun bind(cartItem: CartItem) {
            productName.text = cartItem.productName
            productQuantity.text = "Qty: ${cartItem.quantity}"
            productPrice.text = "Price: $${cartItem.price}"
            productTotal.text = "Total: $${cartItem.quantity * cartItem.price}"
            Picasso.get().load(cartItem.productImage).into(productImage)

            Glide.with(itemView.context)
                .load(cartItem.productImage)
                .placeholder(R.drawable.ic_launcher_background) // Placeholder image while loading
                .error(R.drawable.ic_launcher_background) // Error image if loading fails
                .into(productImage)

            // Load image using an image loading library, e.g., Glide or Picasso
            // Glide.with(itemView).load(cartItem.productImage).into(productImage)
        }
    }
}

class CartItemDiffCallback(
    private val oldList: List<CartItem>,
    private val newList: List<CartItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}