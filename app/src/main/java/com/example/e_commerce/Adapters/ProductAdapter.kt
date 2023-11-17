package com.example.e_commerce.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.R
import com.example.e_commerce.Retrofit.Product

class ProductAdapter(private var categoryData: MutableList<Product>?):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Title: TextView = itemView.findViewById(R.id.title)
        val Brand: TextView = itemView.findViewById(R.id.brand)
        val Price: TextView = itemView.findViewById(R.id.price)
        val Rating: RatingBar = itemView.findViewById(R.id.rating_star)
        val thumbnail: ImageView = itemView.findViewById(R.id.image_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = categoryData?.get(position)
//        val data = categoryData?.get(holder.adapterPosition)
        if (data != null) {
            holder.Title.text = data.title
            holder.Brand.text = data.brand
            holder.Price.text = "$" + data.price.toString()
            holder.Rating.rating = data.rating.toFloat()

            Glide.with(holder.itemView)
                .load(data.thumbnail) // Replace with the actual URL or resource ID of the image
                .placeholder(R.drawable.default_image) // Optional: Placeholder image while loading
                .error(R.drawable.default_image) // Optional: Image to display if loading fails
                .into(holder.thumbnail)
        }
    }

    override fun getItemCount(): Int {
        return categoryData!!.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(newData: List<Product>?) {
        if (categoryData == null) {
            categoryData = mutableListOf()
        }
        newData?.let {
            categoryData?.addAll(it)
            notifyDataSetChanged()
        }
    }
}