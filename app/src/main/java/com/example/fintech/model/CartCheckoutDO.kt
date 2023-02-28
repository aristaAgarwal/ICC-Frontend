package com.example.fintech.model

data class CartCheckoutDO(
    val code: Int,
    val data: Checkout,
    val message: Any,
    val status: Boolean
)