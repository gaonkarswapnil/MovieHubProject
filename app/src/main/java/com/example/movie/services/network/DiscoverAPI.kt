package com.example.movie.services.network

import com.example.movie.model.DiscoverMovieResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverAPI {
    @GET("discover/movie")
    fun discoverMovie(
        @Query("with_genres") genreId: String
    ): Observable<DiscoverMovieResponse>
}