package com.example.movie.repository.apiimplementation

import com.example.movie.model.GenresResponse
import com.example.movie.repository.interfaces.GenreRepository
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class GenreRepositoryImplementation: GenreRepository {

    override fun movieGenre(): Observable<GenresResponse>{
        return RetrofitHelper.getGenres().movieGenre()
    }

}