package com.example.movie.repository.apiimplementation

import androidx.room.PrimaryKey
import androidx.room.util.recursiveFetchLongSparseArray
import com.example.movie.model.Dates
import com.example.movie.model.NowPlayingResponse
import com.example.movie.model.PopularResponse
import com.example.movie.model.TopRatedResponse
import com.example.movie.model.UpcomingMovieResponse
import com.example.movie.repository.interfaces.MovieListRepository
import com.example.movie.services.database.dao.NowPlayingMovieDao
import com.example.movie.services.database.dao.PopularMoviesDao
import com.example.movie.services.database.dao.TopRatedMovieDao
import com.example.movie.services.database.dao.UpcomingMoviesDao
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable
import java.util.Date

class MovieListRepositoryImplementation(
    private val nowPlayingMovieDao: NowPlayingMovieDao,
    private val popularMoviesDao: PopularMoviesDao,
    private val topRatedMoviesDao: TopRatedMovieDao,
    private val upcomingMoviesDao: UpcomingMoviesDao
): MovieListRepository {
    override fun popularMovie(): Observable<PopularResponse> {
        return RetrofitHelper.getMovieList().popularMovie()
            .doOnNext { response ->
                val popular = PopularResponse(
                    page = response.page,
                    results = response.results,
                    total_pages = response.total_pages,
                    total_results = response.total_results
                )

                popularMoviesDao.deleteAllPopularMovies()

                popularMoviesDao.insertPopularMovies(popular)
            }
    }

    override fun getAllPopulatMovies(): Observable<PopularResponse> {
        return Observable.fromCallable { popularMoviesDao.getAllPopularMovies() }
    }

    override fun nowPlaying(): Observable<NowPlayingResponse>{
        return RetrofitHelper.getMovieList().nowPlaying()
            .doOnNext { response->
                val nowPlaying = NowPlayingResponse(
                    dates = Dates(maximum = response.dates.maximum, minimum = response.dates.minimum),
                    page = response.page,
                    results = response.results,
                    total_pages = response.total_pages,
                    total_results = response.total_results
                )

                nowPlayingMovieDao.deleteAllNowPlayingMovies()

                nowPlayingMovieDao.insertNowPlayingMovies(nowPlaying)
            }
    }

    override fun getAllNowPlayingFromDB(): Observable<NowPlayingResponse> {
        return Observable.fromCallable { nowPlayingMovieDao.getAllNowPlayingMovies() }
    }

    override fun topRatedMovies(): Observable<TopRatedResponse>{
        return RetrofitHelper.getMovieList().topRatedMovies()
            .doOnNext { response ->
                val topRatedMovie = TopRatedResponse(
                    page = response.page,
                    results = response.results,
                    total_pages = response.total_pages,
                    total_results = response.total_results
                )

                topRatedMoviesDao.deleteAllTopRatedMovies()

                topRatedMoviesDao.insertTopRatedMovies(topRatedMovie)
            }
    }

    override fun getAllTopRatedMovies(): Observable<TopRatedResponse> {
        return Observable.fromCallable { topRatedMoviesDao.getAllTopRatedMovies() }
    }

    override fun upcomingMovies(): Observable<UpcomingMovieResponse>{
        return RetrofitHelper.getMovieList().upcomingMovies()
            .doOnNext { response ->
                val upcomingMovie = UpcomingMovieResponse(
                    dates = response.dates,
                    page = response.page,
                    results = response.results,
                    total_pages = response.total_pages,
                    total_results = response.total_results
                )

                upcomingMoviesDao.deleteAllUpcomingMovies()
//
                upcomingMoviesDao.insertUpcomingMovies(upcomingMovie)
            }
    }

    override fun getAllUpcomingMovies(): Observable<UpcomingMovieResponse> {
        return Observable.fromCallable { upcomingMoviesDao.getAllUpcomingMovies() }
    }
}