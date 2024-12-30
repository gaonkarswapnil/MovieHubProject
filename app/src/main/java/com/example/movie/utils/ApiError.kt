package com.example.movie.utils

data class ApiError(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)