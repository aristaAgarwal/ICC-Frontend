package com.example.fintech.model

data class AuthenticationDO(
    val code: Int,
    val data: Any,
    val message: Any,
    val status: Boolean
)