package com.example.movie.repository.interfaces

import com.example.movie.model.MovieDetailsResponse
import io.reactivex.rxjava3.core.Observable

interface MoviesRepository {
    fun movieDetail(id: Int): Observable<MovieDetailsResponse>
}