package com.example.e_commerce.Adapters

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerce.R
import com.example.e_commerce.Retrofit.Product

class ProductAdapter(private var categoryData: MutableList<Product>?,   private val itemClickListener: ProductItemClickListener):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Title: TextView = itemView.findViewById(R.id.title)
        val Brand: TextView = itemView.findViewById(R.id.brand)
        val Price: TextView = itemView.findViewById(R.id.price)
        val Rating: RatingBar = itemView.findViewById(R.id.rating_star)
        val thumbnail: ImageView = itemView.findViewById(R.id.image_logo)
        val cardView: ConstraintLayout = itemView.findViewById(R.id.cardView) // Replace with the actual ID of your CardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = categoryData?.get(position)

        updateCardBackground(holder.cardView, holder.itemView.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)

        if (data != null) {
            holder.Title.text = data.title
            holder.Brand.text = data.brand
            holder.Price.text = "$" + data.price.toString()
            holder.Rating.rating = data.rating.toFloat()

            Glide.with(holder.itemView)
                .load(data.thumbnail)
                .placeholder(R.drawable.default_image)
                .error(R.drawable.default_image)
                .into(holder.thumbnail)

            holder.itemView.setOnClickListener {
                itemClickListener.onProductItemClick(data)
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryData!!.size
    }

    private fun updateCardBackground(cardView: ConstraintLayout, nightMode: Int) {
        when (nightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                // Light mode
                cardView.setBackgroundResource(R.drawable.card_background)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                // Dark mode
                cardView.setBackgroundResource(R.drawable.dark_mode_card_background)
            }
            else -> {
                // Default to light mode
                cardView.setBackgroundResource(R.drawable.card_background)
            }
        }
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

    interface ProductItemClickListener {
        fun onProductItemClick(product: Product)
    }
}