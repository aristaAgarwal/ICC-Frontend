package com.example.fintech.network

import com.example.fintech.model.AddProductToCart
import com.example.fintech.model.BaseResponseDO
import com.example.fintech.model.CartDO
import com.example.fintech.model.ProductsDO
import okhttp3.Cookie
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PostAddProduct {

    @POST("cart/add")
    suspend fun postAddProduct(
        @Body addProduct: AddProductToCart,
        @Header("Cookie") cookies: String
    ): Response<CartDO>
}