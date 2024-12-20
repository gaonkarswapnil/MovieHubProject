package com.example.movie.repository.interfaces

import com.example.movie.model.TrendingMovieResponse
import io.reactivex.rxjava3.core.Observable

interface TrendingRepository {
    fun treadingMovie(): Observable<TrendingMovieResponse>

    fun getAllTrendingMovie(): Observable<TrendingMovieResponse>
}