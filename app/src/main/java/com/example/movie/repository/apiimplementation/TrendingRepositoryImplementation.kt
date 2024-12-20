package com.example.movie.repository.apiimplementation

import com.example.movie.model.TrendingMovieResponse
import com.example.movie.repository.interfaces.TrendingRepository
import com.example.movie.services.database.dao.TrendingMovieDao
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class TrendingRepositoryImplementation(
    private val trendingMovieDao: TrendingMovieDao
): TrendingRepository {

    override fun treadingMovie(): Observable<TrendingMovieResponse>{
        return RetrofitHelper.getTrending().treadingMovie()
            .doOnNext {response ->
                val trendingMovies = TrendingMovieResponse(
                    page = response.page,
                    results = response.results,
                    total_pages = response.total_pages,
                    total_results = response.total_results
                )

                trendingMovieDao.deleteAllTrendingMovies()

                trendingMovieDao.insertTrendingMovies(trendingMovies)
            }
    }

    override fun getAllTrendingMovie(): Observable<TrendingMovieResponse> {
        return Observable.fromCallable { trendingMovieDao.getAllTrendingMovies() }
    }

}