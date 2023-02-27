package com.example.fintech.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ProductsDO(
    val code: Int,
    val data: AllProductsDO,
    val message: String?,
    val status: Boolean
): Parcelable

