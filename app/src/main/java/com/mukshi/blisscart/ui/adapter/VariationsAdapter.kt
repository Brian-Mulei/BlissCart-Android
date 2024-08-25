package com.mukshi.blisscart.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.Variation

class VariationsAdapter   (private val variations: List<Variation>,
private val onVariationSelected: (Variation) -> Unit
) : RecyclerView.Adapter<VariationsAdapter.VariationViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_variation, parent, false)
        return VariationViewHolder(view)
    }

    override fun onBindViewHolder(holder: VariationViewHolder, position: Int) {
        val variation = variations[position]


        holder.variationDescriptionTextView.text = variation.variationDescription
        holder.itemView.isSelected = selectedPosition == position
        holder.itemView.setOnClickListener {
            if (selectedPosition != holder.adapterPosition) {
                notifyItemChanged(selectedPosition)
                selectedPosition = holder.adapterPosition
                notifyItemChanged(selectedPosition)
                onVariationSelected(variation)
            }
        }
    }

    override fun getItemCount(): Int = variations.size

    class VariationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val variationDescriptionTextView: TextView = itemView.findViewById(R.id.variationDescriptionTextView)


    }
}