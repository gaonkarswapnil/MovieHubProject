package com.example.movie.repository.interfaces

import com.example.movie.model.Genre
import com.example.movie.model.GenresResponse
import io.reactivex.rxjava3.core.Observable

interface GenreRepository {
    fun movieGenre(): Observable<GenresResponse>

    fun getMovieGenre(): Observable<GenresResponse>
}