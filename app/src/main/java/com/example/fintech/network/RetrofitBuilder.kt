package com.example.fintech.network

import com.example.fintech.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val baseUrl = "https://icc-hack.ap-south-1.elasticbeanstalk.com"

//    val baseUrl = "http://" +
////            BuildConfig.IP +
//             "10.0.2.2" +
//            ":8080/"

    val client = OkHttpClient.Builder()
        .build()

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}