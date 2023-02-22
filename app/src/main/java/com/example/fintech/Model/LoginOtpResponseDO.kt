package com.example.fintech.Model

data class LoginOtpResponseDO(
    val code: Int,
    val `data`: Any,
    val message: Any,
    val status: Boolean
)