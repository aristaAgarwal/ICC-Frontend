package com.example.fintech.Model

data class AuthenticationDO(
    val code: Int,
    val `data`: Data,
    val message: Any,
    val status: Boolean
)