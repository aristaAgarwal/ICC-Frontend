package com.example.fintech.network

import com.example.fintech.model.AddProductToCart
import com.example.fintech.model.CartDO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CartProduct {

    @POST("cart/add")
    suspend fun postAddProduct(
        @Body addProduct: AddProductToCart,
        @Header("Cookie") cookies: String
    ): Response<CartDO>

    @GET("cart/info")
    suspend fun getAllProduct(
        @Header("Cookie") cookies: String
    ): Response<CartDO>

    @POST("cart/remove")
    suspend fun removeProduct(
        @Body removeProduct: AddProductToCart,
        @Header("Cookie") cookies: String
    ): Response<CartDO>
}