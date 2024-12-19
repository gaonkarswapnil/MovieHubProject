package com.example.movie.repository.apiimplementation

import com.example.movie.model.NowPlayingResponse
import com.example.movie.model.PopularResponse
import com.example.movie.model.TopRatedResponse
import com.example.movie.model.UpcomingMovieResponse
import com.example.movie.repository.interfaces.MovieListRepository
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class MovieListRepositoryImplementation: MovieListRepository {
    override fun popularMovie(): Observable<PopularResponse> {
        return RetrofitHelper.getMovieList().popularMovie()
    }

    override fun nowPlaying(): Observable<NowPlayingResponse>{
        return RetrofitHelper.getMovieList().nowPlaying()
    }

    override fun topRatedMovies(): Observable<TopRatedResponse>{
        return RetrofitHelper.getMovieList().topRatedMovies()
    }

    override fun upcomingMovies(): Observable<UpcomingMovieResponse>{
        return RetrofitHelper.getMovieList().upcomingMovies()
    }
}