package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.MovieDetailsResponse
import com.example.movie.repository.interfaces.MoviesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel(
    val application: Application,
    val moviesRepository: MoviesRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun movieDetail(id: Int): LiveData<MovieDetailsResponse>{
        val movieDetailResponse = MutableLiveData<MovieDetailsResponse>()

        moviesRepository.movieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movieDetail ->
                movieDetailResponse.value = movieDetail
            },{error ->
                Log.d("TrendingMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
            }).addTo(compositeDisposable)

        return movieDetailResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}