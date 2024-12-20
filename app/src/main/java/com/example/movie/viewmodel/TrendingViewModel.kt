package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.TrendingMovieResponse
import com.example.movie.repository.interfaces.TrendingRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class TrendingViewModel(
    val application: Application,
    val trendingRepository: TrendingRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun treadingMovie(): LiveData<TrendingMovieResponse>{
        val trendingMovieResponse = MutableLiveData<TrendingMovieResponse>()

        trendingRepository.treadingMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movie ->
                trendingMovieResponse.value = movie
            },{error ->
                Log.d("TrendingMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                trendingRepository.getAllTrendingMovie()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({nowPlaying ->
                        trendingMovieResponse.value = nowPlaying
                    },{
                        it.printStackTrace()
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return trendingMovieResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}