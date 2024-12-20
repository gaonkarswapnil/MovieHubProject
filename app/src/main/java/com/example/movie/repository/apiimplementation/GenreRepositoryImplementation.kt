package com.example.movie.repository.apiimplementation

import com.example.movie.model.GenresResponse
import com.example.movie.repository.interfaces.GenreRepository
import com.example.movie.services.database.dao.GenreDao
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class GenreRepositoryImplementation(
    private val genreDao: GenreDao
): GenreRepository {

    override fun movieGenre(): Observable<GenresResponse>{
        return RetrofitHelper.getGenres().movieGenre()
            .doOnNext { response ->
                val genre = GenresResponse(
                    genres = response.genres
                )

                genreDao.deleteAllGenre()

                genreDao.insertGenre(genre)
            }
    }

    override fun getMovieGenre(): Observable<GenresResponse> {
        return Observable.fromCallable { genreDao.getAllGenre() }
    }

}