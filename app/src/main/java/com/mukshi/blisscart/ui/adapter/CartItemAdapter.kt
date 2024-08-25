package com.mukshi.blisscart.ui.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.CartItem

class CartItemAdapter(
    private var cartItems: List<CartItem>,
    private val onAddClick: (CartItem) -> Unit,
    private val onRemoveClick: (CartItem) -> Unit,
    private val onDeleteClick: (CartItem) -> Unit
) : RecyclerView.Adapter<CartItemAdapter.CartViewHolder>() {

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.total_price)
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productQuantity: TextView = itemView.findViewById(R.id.product_quantity)
        val btnAdd: Button = itemView.findViewById(R.id.btn_add)
        val btnRemove: Button = itemView.findViewById(R.id.btn_remove)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)

    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.productName.text = cartItem.productName
        holder.productPrice.text = "KES ${cartItem.quantity * cartItem.price}"
        holder.productQuantity.text = cartItem.quantity.toString()

        // Load product image using Glide
//        Glide.with(holder.itemView.context)
//            .load(cartItem.productImage)
//            .placeholder(R.drawable.ic_placeholder) // Placeholder image while loading
//            .error(R.drawable.ic_placeholder) // Error image if loading fails
//            .into(holder.productImage)

        // Handle the add button click
        holder.btnAdd.setOnClickListener {
            onAddClick(cartItem)
        }

        // Handle the remove button click
        holder.btnRemove.setOnClickListener {
            if (cartItem.quantity > 1) {
                onRemoveClick(cartItem)
            } else {
                // Show confirmation dialog before removing the item completely
                onDeleteClick(cartItem)

            }
        }

        // Handle the delete button click
        holder.btnDelete.setOnClickListener {
            onDeleteClick(cartItem)
        }
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    fun updateList(newCartItems: List<CartItem>) {
        this.cartItems = newCartItems
        notifyDataSetChanged()
    }
}
