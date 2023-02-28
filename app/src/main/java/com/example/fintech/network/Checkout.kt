package com.example.fintech.network

import com.example.fintech.model.CartCheckoutDO
import com.example.fintech.model.CartIdDO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Checkout {
    @POST("order/checkout")
    suspend fun checkout(
        @Body cartIdDO: CartIdDO,
        @Header("Cookie") cookie: String
    ):Response<CartCheckoutDO>
}