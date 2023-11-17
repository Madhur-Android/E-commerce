package com.example.e_commerce.Retrofit

data class CategoryResponse(
    val products: MutableList<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)