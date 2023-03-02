package com.example.fintech.model

data class CartDO(
    val code: Int,
    val data: AddCartData?,
    val message: String?,
    val status: Boolean
)
