package com.example.fintech.model

data class VideosDO(
    val code: Int,
    val `data`: List<VideosData>,
    val message: Any,
    val status: Boolean
)