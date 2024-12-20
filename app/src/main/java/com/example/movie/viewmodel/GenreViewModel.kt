package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.GenresResponse
import com.example.movie.repository.apiimplementation.GenreRepositoryImplementation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class GenreViewModel(
    val application: Application,
    val genreRepository: GenreRepositoryImplementation
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun movieGenre(): LiveData<GenresResponse>{
        val movieGenresResponse = MutableLiveData<GenresResponse>()

        genreRepository.movieGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movie ->
                movieGenresResponse.value = movie
            },{error ->
                Log.d("Genre Movie", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                genreRepository.getMovieGenre()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({genre ->
                        movieGenresResponse.value = genre
                    },{
                        it.printStackTrace()
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return movieGenresResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}