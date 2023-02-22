package com.example.fintech.Model

data class OtpVerifiedResponseDO(
    val code: Int,
    val `data`: DataX,
    val message: Any,
    val status: Boolean
)