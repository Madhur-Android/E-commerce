package com.example.e_commerce.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.e_commerce.R

class ImageSliderAdapter(private val imageList: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSliderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_slider, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        val imageUrl = imageList[position]

        // Using Glide to load images from URLs
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .apply(RequestOptions.centerCropTransform())
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageSliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
