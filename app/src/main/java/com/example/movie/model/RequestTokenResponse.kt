package com.example.movie.model

data class RequestTokenResponse (
    val success: Boolean,
    val expiresAt: String,
    val requestToken: String
)