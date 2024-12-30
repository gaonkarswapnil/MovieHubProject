package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.DiscoverMovieResponse
import com.example.movie.repository.interfaces.DiscoverRepository
import com.example.movie.utils.ApiError
import com.example.movie.utils.NetworkData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class DiscoverViewModel(
    private val application: Application,
    private val discoverRepository: DiscoverRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun discoverMovie(genreId: String): LiveData<NetworkData<DiscoverMovieResponse>>{
        val discoverMovieResponse = MutableLiveData<NetworkData<DiscoverMovieResponse>>()

        discoverRepository.discoverMovie(genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movie ->
                discoverMovieResponse.value = NetworkData.Success(movie)
            },{error ->
//                Log.d("Discover Movie", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                discoverRepository.getAllDiscoverMovie()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({discover ->
                        Log.d("Discovered Movie","${(discover as? HttpException)?.code() ?: -1}")
                        discoverMovieResponse.value = NetworkData.Success(discover)
                    },{
                        discoverMovieResponse.value = NetworkData.Error(
                            ApiError(
                                status_code = (it as? HttpException)?.code() ?: -1,
                                status_message = (it as? HttpException)?.message ?: "Unknown Message",
                                success = false
                            )
                        )
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return discoverMovieResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}