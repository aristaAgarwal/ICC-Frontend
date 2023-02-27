package com.example.fintech.model

import android.os.Parcelable

data class Product(
    val _id: String,
    val created_at: Double,
    val deleted: Boolean,
    val description: String,
    val discount: Int,
    val display_image: String,
    val images: List<String>,
    val is_sku: Boolean,
    val name: String,
    val parent: String,
    val price: Int,
    val published: Boolean,
    val quantity: Int,
    val ratings: Float,
    val sizes: List<String>,
    val updated_at: Double,
    val user_id: String,
    val uuid: String
): java.io.Serializable