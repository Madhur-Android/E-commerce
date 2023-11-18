package com.example.e_commerce

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.RatingBar
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.e_commerce.Adapters.ImageSliderAdapter
import com.example.e_commerce.Retrofit.Product
import org.w3c.dom.Text

class ProductDetailActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT_KEY = "product"
    }
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ImageSliderAdapter
    private var currentPage = 0
    private var handler: Handler? = null
    private var runnable: Runnable? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val product = intent.getParcelableExtra<Product>(PRODUCT_KEY)
        val titleTextView: TextView = findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = findViewById(R.id.descriptionTextView)
        val priceTextView: TextView = findViewById(R.id.priceTextView)
        val discountTextView: TextView = findViewById(R.id.discountTextView)
        val ratingBar: RatingBar = findViewById(R.id.ratingBar)
        val category: TextView = findViewById(R.id.category)
        val Brand: TextView = findViewById(R.id.Brand)

        viewPager = findViewById(R.id.viewPager)

        if (product != null && product.images.isNotEmpty()) {
            adapter = ImageSliderAdapter(product.images)
            viewPager.adapter = adapter
            startAutoSlide()
        }

        product?.let {
            titleTextView.text = it.title
            descriptionTextView.text = it.description
            priceTextView.text = "$${it.price}"
            discountTextView.text = "${it.discountPercentage}% off"
            ratingBar.rating = it.rating.toFloat()
            category.text = it.category
            Brand.text = it.brand
        }
    }

    private fun startAutoSlide() {
        handler = Handler(mainLooper)
        runnable = object : Runnable {
            override fun run() {
                if (currentPage == adapter.itemCount - 1) {
                    currentPage = 0
                } else {
                    currentPage++
                }
                viewPager.setCurrentItem(currentPage, true)
                handler?.postDelayed(this, 3000)
            }
        }
        handler?.postDelayed(runnable!!, 3000)
    }

    override fun onDestroy() {
        super.onDestroy()

        handler?.removeCallbacks(runnable!!)
    }
}