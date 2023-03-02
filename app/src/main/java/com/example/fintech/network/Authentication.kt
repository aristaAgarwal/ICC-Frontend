package com.example.fintech.network

import com.example.fintech.model.*
import retrofit2.Response
import retrofit2.http.*

interface Authentication {

    @POST("auth/google/callback")
    suspend fun authentication(
        @Body idToken: IdToken
    ): Response<BaseResponseDO>

    @POST("auth/send/otp")
    suspend fun otpAuthentication(
        @Body phone: Phone
    ): Response<BaseResponseDO>

    @POST("auth/verify/otp")
    suspend fun otpVerification(
        @Body verifyOtpDO: VerifyOtpDO
    ): Response<BaseResponseDO>

    @POST("auth/logout")
    suspend fun logout(
        @Header("Cookie") cookie: String
    ): Response<LogoutDO>

    @GET("user")
    suspend fun getUserInfo(
        @Header("Cookie") cookie: String
    ): Response<BaseResponseDO>

    @GET("referral")
    suspend fun checkReferral(
        @Query("code") code: String
    ):Response<BaseResponseDO>
}