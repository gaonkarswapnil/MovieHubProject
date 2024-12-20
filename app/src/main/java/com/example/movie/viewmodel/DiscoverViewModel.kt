package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.DiscoverMovieResponse
import com.example.movie.repository.interfaces.DiscoverRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class DiscoverViewModel(
    val application: Application,
    val discoverRepository: DiscoverRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun discoverMovie(genreId: String): LiveData<DiscoverMovieResponse>{
        val discoverMovieResponse = MutableLiveData<DiscoverMovieResponse>()

        discoverRepository.discoverMovie(genreId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movie ->
                discoverMovieResponse.value = movie
            },{error ->
//                Log.d("Discover Movie", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                discoverRepository.getAllDiscoverMovie()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({discover ->
                        discoverMovieResponse.value = discover
                    },{
                        it.printStackTrace()
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return discoverMovieResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}