package com.example.movie.model

data class CreateSessionLoginRequest(
    val password: String,
    val request_token: String,
    val username: String
)