package com.example.movie.services.network

import com.example.movie.model.GenresResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface GenreAPI {

    @GET("genre/movie/list")
    fun movieGenre(): Observable<GenresResponse>

}