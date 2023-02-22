package com.example.fintech.network

import com.example.fintech.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Authentication {

    @POST("auth/google/callback")
    suspend fun authentication(
        @Body idToken: IdToken
    ): Response<AuthenticationDO>

    @POST("auth/send/otp")
    suspend fun otpAuthentication(
        @Body phone: Phone
    ): Response<AuthenticationDO>

    @POST("auth/verify/otp")
    suspend fun otpVerification(
        @Body verifyOtpDO: VerifyOtpDO
    ): Response<AuthenticationDO>

    @POST("auth/logout")
    suspend fun logout(
        @Header("Cookies") cookies: String
    ): Response<AuthenticationDO>


}