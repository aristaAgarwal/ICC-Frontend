package com.example.fintech.network

import retrofit2.http.GET

interface ReferralCheck {
    @GET("referral?code=PLQWJL")
    suspend fun checkReferral(

    )
}