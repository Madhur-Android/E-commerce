package com.example.e_commerce.Retrofit

data class CategoryResponse(
    val products: List<Product>,
    val total: Int,
    val skip: Int,
    val limit: Int
)