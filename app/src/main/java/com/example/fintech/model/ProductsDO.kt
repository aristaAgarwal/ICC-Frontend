package com.example.fintech.model

data class ProductsDO(
    val code: Int,
    val data: List<Product>,
    val message: String?,
    val status: Boolean
)

