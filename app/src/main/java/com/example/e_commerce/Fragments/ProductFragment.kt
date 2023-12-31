package com.example.e_commerce.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.Adapters.ProductAdapter
import com.example.e_commerce.ProductDetailActivity
import com.example.e_commerce.R
import com.example.e_commerce.Retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : Fragment(), ProductAdapter.ProductItemClickListener {

    companion object {
        fun newInstance(category: String): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putString("category", category)
            fragment.arguments = args
            return fragment
        }
    }
    private var subCategory: String? = null
    private lateinit var recyclerView: RecyclerView
    private var currentPage: Int = 0
    private var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_product, container, false)

        recyclerView = view.findViewById(R.id.product_rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        currentPage = 0
        subCategory = arguments?.getString("category")



        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                    loadNextPage(subCategory)
                }
            }
        })

        makeApiCall(subCategory)
        return view
    }

    private fun makeApiCall(subCategory: String?) {
        val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)
        val call = apiService.getCategoryData(subCategory ?: "", 5, currentPage * 5)

        call.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                if (response.isSuccessful) {

                    val categoryResponse = response.body()
                    val categoryData = categoryResponse?.products

                    if (categoryData != null) {
                        val adapter = ProductAdapter(categoryData, this@ProductFragment)
                        recyclerView.adapter = adapter
                        adapter.addData(categoryData)
                        currentPage++


                    }
                } else {
                }
                isLoading = false
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                isLoading = false
            }
        })
    }

    private fun loadNextPage(subCategory: String?) {
        isLoading = true
        makeApiCall(subCategory)
    }

    override fun onProductItemClick(product: Product) {
        val intent = Intent(requireContext(), ProductDetailActivity::class.java)
        intent.putExtra(ProductDetailActivity.PRODUCT_KEY, product)
        startActivity(intent)
    }

}