package com.example.movie.utils

sealed class NetworkData<T>(val data: T? = null, val error: ApiError? = null) {
    class Success<T>(data: T): NetworkData<T>(data)

    class Error<T>(error: ApiError?= null): NetworkData<T>(error=error)
}