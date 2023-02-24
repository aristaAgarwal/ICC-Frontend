package com.example.fintech.network

import com.example.fintech.model.BaseResponseDO
import com.example.fintech.model.ProductsDO
import retrofit2.Response
import retrofit2.http.GET

interface GetAllProducts {

    @GET("products")
    suspend fun getAllProducts(): Response<ProductsDO>
}