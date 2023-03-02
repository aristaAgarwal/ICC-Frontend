package com.example.fintech.network


import com.example.fintech.model.VideosDO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetTutorials {
    @GET("videos")
    suspend fun getTutorials(
        @Header("Cookie") cookies: String,
        @Query("type") type: String
    ): Response<VideosDO>
}