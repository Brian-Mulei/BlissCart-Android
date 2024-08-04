package com.mukshi.blisscart.ui.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukshi.blisscart.App
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.CartItem
import com.mukshi.blisscart.ui.adapter.CartItemAdapter
import com.mukshi.blisscart.ui.viewmodel.CartViewModel
import com.mukshi.blisscart.ui.viewmodel.CartViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CartFragment : Fragment() {
    private val cartViewModel: CartViewModel by viewModels {
        CartViewModelFactory(
            App.instance.getDatabase().cartDao())
    }

    private lateinit var cartAdapter: CartItemAdapter
    private lateinit var totalPriceTextView: TextView
    private lateinit var goToCheckoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.cart_recycler_view)
        totalPriceTextView = view.findViewById(R.id.total_price)
        goToCheckoutButton = view.findViewById(R.id.go_to_checkout_button)

        cartAdapter = CartItemAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = cartAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            cartAdapter.apply {
                this.cartItems.clear()
                this.cartItems.addAll(cartItems)
                notifyDataSetChanged()
            }
            updateTotalPrice(cartItems)
        }

//        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
//            cartAdapter.setCartItems(cartItems)
//        }
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            private val deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)
            private val intrinsicWidth = deleteIcon?.intrinsicWidth ?: 0
            private val intrinsicHeight = deleteIcon?.intrinsicHeight ?: 0
            private val background = ColorDrawable(Color.RED)
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val cartItem = cartAdapter.getCartItemAtPosition(position)

                 cartViewModel.removeCartItem(cartItem.productId)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                // Draw the red delete background
                background.setBounds(
                    if (dX > 0) itemView.left else itemView.right + dX.toInt(),
                    itemView.top,
                    if (dX > 0) itemView.left + dX.toInt() else itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                // Calculate position of delete icon
                val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
                val deleteIconLeft = if (dX > 0) itemView.left + deleteIconMargin else itemView.right - deleteIconMargin - intrinsicWidth
                val deleteIconRight = if (dX > 0) itemView.left + deleteIconMargin + intrinsicWidth else itemView.right - deleteIconMargin
                val deleteIconBottom = deleteIconTop + intrinsicHeight

                // Draw the delete icon
                deleteIcon?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
                deleteIcon?.draw(c)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)



        goToCheckoutButton.setOnClickListener {
            // Navigate to checkout or handle the action
            // For example, using Navigation Component:
            // findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
    }

    private fun updateTotalPrice(cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        totalPriceTextView.text = "Total: KES ${"%.2f".format(totalPrice)}"
    }
}