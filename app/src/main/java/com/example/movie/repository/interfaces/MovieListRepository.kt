package com.example.movie.repository.interfaces

import com.example.movie.model.NowPlayingResponse
import com.example.movie.model.PopularResponse
import com.example.movie.model.TopRatedResponse
import com.example.movie.model.UpcomingMovieResponse
import io.reactivex.rxjava3.core.Observable

interface MovieListRepository {
    fun popularMovie(): Observable<PopularResponse>

    fun nowPlaying(): Observable<NowPlayingResponse>

    fun topRatedMovies(): Observable<TopRatedResponse>

    fun upcomingMovies(): Observable<UpcomingMovieResponse>
}