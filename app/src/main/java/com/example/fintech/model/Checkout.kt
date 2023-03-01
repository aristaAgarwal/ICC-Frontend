package com.example.fintech.model

data class Checkout(
    val _id: String,
    val cart_id: String,
    val created_at: Double,
    val deleted: Boolean,
    val discount: Any,
    val final_price: Any,
    val meta: Meta,
    val payment_id: String,
    val payment_status: Any,
    val price: Int,
    val status: Int,
    val updated_at: Double,
    val user_id: String
)