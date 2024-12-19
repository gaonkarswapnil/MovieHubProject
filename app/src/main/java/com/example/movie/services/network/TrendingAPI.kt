package com.example.movie.services.network

import com.example.movie.model.TrendingMovieResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface TrendingAPI {

    @GET("trending/movie/day")
    fun treadingMovie(): Observable<TrendingMovieResponse>

}