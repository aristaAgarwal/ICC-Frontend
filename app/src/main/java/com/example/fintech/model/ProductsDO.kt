package com.example.fintech.model

data class ProductsDO(
    val code: Int,
    val data: AllProductsDO,
    val message: Any,
    val status: Boolean
)
