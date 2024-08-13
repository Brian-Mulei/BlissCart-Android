package com.mukshi.blisscart.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.mukshi.blisscart.R
import com.mukshi.blisscart.data.model.Images

class ProductImagesAdapter( private val images: List<Images>) : RecyclerView.Adapter<ProductImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = images[position].image_url
        // Use Glide or Picasso to load images
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
