package com.example.movie.repository.apiimplementation

import com.example.movie.model.TrendingMovieResponse
import com.example.movie.repository.interfaces.TrendingRepository
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class TrendingRepositoryImplementation: TrendingRepository {

    override fun treadingMovie(): Observable<TrendingMovieResponse>{
        return RetrofitHelper.getTrending().treadingMovie()
    }

}