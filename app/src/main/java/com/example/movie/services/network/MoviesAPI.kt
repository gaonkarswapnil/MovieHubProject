package com.example.movie.services.network

import com.example.movie.model.MovieDetailsResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesAPI {

    @GET("movie/{movie_id}")
    fun details(
        @Path("movie_id") movie_id: Int
    ): Observable<MovieDetailsResponse>

}