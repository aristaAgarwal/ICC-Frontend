package com.example.fintech.model

data class UserCartDO(
    val _id: String,
    val created_at: Double,
    val deleted: Boolean,
    val products: List<Product>,
    val updated_at: Double,
    val user_id: String
)