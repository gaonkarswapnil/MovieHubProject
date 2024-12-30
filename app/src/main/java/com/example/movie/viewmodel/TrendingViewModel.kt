package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.TrendingMovieResponse
import com.example.movie.repository.interfaces.TrendingRepository
import com.example.movie.utils.ApiError
import com.example.movie.utils.NetworkData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class TrendingViewModel(
    private val application: Application,
    private val trendingRepository: TrendingRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun treadingMovie(): LiveData<NetworkData<TrendingMovieResponse>>{
        val trendingMovieResponse = MutableLiveData<NetworkData<TrendingMovieResponse>>()

        trendingRepository.treadingMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movie ->
                trendingMovieResponse.value = NetworkData.Success(movie)
            },{error ->
                Log.d("TrendingMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                trendingRepository.getAllTrendingMovie()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({nowPlaying ->
                        trendingMovieResponse.value = NetworkData.Success(nowPlaying)
                    },{
                        trendingMovieResponse.value = NetworkData.Error(
                            error = ApiError(
                                status_code = (it as HttpException).code() ?: -1,
                                status_message = (it as HttpException).message ?: "Unknown Message",
                                success = false
                            )
                        )
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return trendingMovieResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}