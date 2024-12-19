package com.example.movie.services.network

import com.example.movie.model.NowPlayingResponse
import com.example.movie.model.PopularResponse
import com.example.movie.model.TopRatedResponse
import com.example.movie.model.UpcomingMovieResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface MovieListApi {
    @GET("movie/popular")
    fun popularMovie(): Observable<PopularResponse>

    @GET("movie/now_playing")
    fun nowPlaying(): Observable<NowPlayingResponse>

    @GET("movie/top_rated")
    fun topRatedMovies(): Observable<TopRatedResponse>

    @GET("movie/upcoming")
    fun upcomingMovies(): Observable<UpcomingMovieResponse>
}