package com.example.movie.repository.interfaces

import com.example.movie.model.DiscoverMovieResponse
import io.reactivex.rxjava3.core.Observable

interface DiscoverRepository {
    fun discoverMovie(genreId: String): Observable<DiscoverMovieResponse>

    fun getAllDiscoverMovie(): Observable<DiscoverMovieResponse>
}