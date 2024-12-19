package com.example.movie.repository.apiimplementation

import com.example.movie.model.MovieDetailsResponse
import com.example.movie.repository.interfaces.MoviesRepository
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class MoviesRepositoryImplementation: MoviesRepository {

    override fun movieDetail(id: Int): Observable<MovieDetailsResponse>{
        return RetrofitHelper.getMovies().details(id)
    }
}