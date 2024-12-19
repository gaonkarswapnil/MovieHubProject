package com.example.movie.repository.apiimplementation

import com.example.movie.model.DiscoverMovieResponse
import com.example.movie.repository.interfaces.DiscoverRepository
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class DiscoverRepositoryImplementation: DiscoverRepository {
    override fun discoverMovie(genreId: String): Observable<DiscoverMovieResponse>{
        return RetrofitHelper.getDiscover().discoverMovie(genreId)
    }
}