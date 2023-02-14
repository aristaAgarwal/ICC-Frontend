package com.example.fintech.network

import com.example.fintech.Model.AuthenticationDO
import com.example.fintech.Model.IdToken
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Authentication {

    @POST("auth/google/callback")
    suspend fun authentication(
        @Body idToken: IdToken
    ): Response<AuthenticationDO>

    @POST("auth/logout")
    suspend fun logout(
        @Body idToken: IdToken
    ): Response<AuthenticationDO>
}