package com.example.e_commerce.Retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("categories")
    fun getCategories(): Call<List<String>>

    @GET("category/{category}")
    fun getCategoryData(@Path("category") category: String): Call<CategoryResponse>
}