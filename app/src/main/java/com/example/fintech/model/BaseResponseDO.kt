package com.example.fintech.model

data class BaseResponseDO(
    val code: Int,
    val data: Data,
    val message: Any,
    val status: Boolean
)