package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.MovieDetailsResponse
import com.example.movie.repository.interfaces.MoviesRepository
import com.example.movie.utils.ApiError
import com.example.movie.utils.NetworkData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class MoviesViewModel(
    private val application: Application,
    private val moviesRepository: MoviesRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun movieDetail(id: Int): LiveData<NetworkData<MovieDetailsResponse>>{
        val movieDetailResponse = MutableLiveData<NetworkData<MovieDetailsResponse>>()

        moviesRepository.movieDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movieDetail ->
                movieDetailResponse.value = NetworkData.Success(movieDetail)
            },{
                Log.d("MovieDetailsList", "Error")
//                movieDetailResponse.value = NetworkData.Error(
//                    error = ApiError(
//                        status_code = (error as HttpException).code() ?: -1,
//                        status_message = (error as HttpException).message ?: "Unknown Message",
//                        success = false
//                    )
//                )
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
            }).addTo(compositeDisposable)

        return movieDetailResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}