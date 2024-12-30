package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.GenresResponse
import com.example.movie.repository.apiimplementation.GenreRepositoryImplementation
import com.example.movie.utils.ApiError
import com.example.movie.utils.NetworkData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class GenreViewModel(
    private val application: Application,
    private val genreRepository: GenreRepositoryImplementation
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun movieGenre(): LiveData<NetworkData<GenresResponse>>{
        val movieGenresResponse = MutableLiveData<NetworkData<GenresResponse>>()

        genreRepository.movieGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({movie ->
                movieGenresResponse.value = NetworkData.Success(movie)
            },{error ->
                Log.d("Genre Movie", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                genreRepository.getMovieGenre()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({genre ->
                        movieGenresResponse.value = NetworkData.Success(genre)
                    },{
                        movieGenresResponse.value = NetworkData.Error(
                            error = ApiError(
                                status_code = (it as HttpException).code() ?: -1,
                                status_message = (it as HttpException).message() ?: "Unknown Message",
                                success = false
                            )
                        )
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return movieGenresResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}