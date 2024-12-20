package com.example.movie.repository.apiimplementation

import com.example.movie.model.DiscoverMovieResponse
import com.example.movie.repository.interfaces.DiscoverRepository
import com.example.movie.services.database.dao.DiscoverMovieDao
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class DiscoverRepositoryImplementation(
    private val discoverMovieDao: DiscoverMovieDao
): DiscoverRepository {
    override fun discoverMovie(genreId: String): Observable<DiscoverMovieResponse>{
        return RetrofitHelper.getDiscover().discoverMovie(genreId)
            .doOnNext { response ->
                val discover = DiscoverMovieResponse(
                    page = response.page,
                    results = response.results,
                    total_pages = response.total_pages,
                    total_results = response.total_results
                )

                discoverMovieDao.deleteAllDiscoverMovie()

                discoverMovieDao.insertDiscoverMovie(discover)
            }
    }

    override fun getAllDiscoverMovie(): Observable<DiscoverMovieResponse> {
        return Observable.fromCallable { discoverMovieDao.getAllDiscoverMovie() }
    }
}