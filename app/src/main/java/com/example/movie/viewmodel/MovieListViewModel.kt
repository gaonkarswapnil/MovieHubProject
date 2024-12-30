package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import android.util.NoSuchPropertyException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.NowPlayingResponse
import com.example.movie.model.PopularResponse
import com.example.movie.model.TopRatedResponse
import com.example.movie.model.UpcomingMovieResponse
import com.example.movie.repository.interfaces.MovieListRepository
import com.example.movie.utils.ApiError
import com.example.movie.utils.NetworkData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

class MovieListViewModel(
    private val application: Application,
    private val movieListRepository: MovieListRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun popularMovie(): LiveData<NetworkData<PopularResponse>>{
        val popularResponse = MutableLiveData<NetworkData<PopularResponse>>()

        movieListRepository.popularMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({popularMovie ->
                popularResponse.value = NetworkData.Success(popularMovie)
            }, {error ->
                Log.d("PopularMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllPopulatMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({popular ->
                        popularResponse.value = NetworkData.Success(popular)
                    },{
                        popularResponse.value = NetworkData.Error(
                            error = ApiError(
                                status_code = (it as HttpException).code() ?: -1,
                                status_message = (it as HttpException).message ?: "Unknown Message",
                                success = false
                            )
                        )
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return popularResponse
    }

    fun nowPlaying(): LiveData<NetworkData<NowPlayingResponse>>{
        val nowPlayingResponse = MutableLiveData<NetworkData<NowPlayingResponse>>()

        movieListRepository.nowPlaying()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({nowPlaying ->
                nowPlayingResponse.value = NetworkData.Success(nowPlaying)
            },{error ->
                Log.d("NowPlayingMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllNowPlayingFromDB()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({nowPlaying ->
                        nowPlayingResponse.value = NetworkData.Success(nowPlaying)
                    },{
                        nowPlayingResponse.value = NetworkData.Error(
                            error = ApiError(
                                status_code = (it as HttpException).code() ?: -1,
                                status_message = (it as HttpException).message ?: "Unknown Message",
                                success = false
                            )
                        )
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return nowPlayingResponse
    }

    fun topRatedMovies(): LiveData<NetworkData<TopRatedResponse>>{
        val topRatedResponse = MutableLiveData<NetworkData<TopRatedResponse>>()

        movieListRepository.topRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({topRatedMovie ->
                topRatedResponse.value = NetworkData.Success(topRatedMovie)
            },{error ->
                Log.d("TopRatedMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllTopRatedMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({topRated ->
                        topRatedResponse.value = NetworkData.Success(topRated)
                    },{
                        topRatedResponse.value = NetworkData.Error(
                            error = ApiError(
                                status_code = (it as HttpException).code() ?: -1,
                                status_message = (it as HttpException).message ?: "Unknown Message",
                                success = false
                            )
                        )
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return topRatedResponse
    }

    fun upcomingMovies(): LiveData<NetworkData<UpcomingMovieResponse>>{
        val upcomingMovieResponse = MutableLiveData<NetworkData<UpcomingMovieResponse>>()

        movieListRepository.upcomingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({upcomingMovie ->
                upcomingMovieResponse.value = NetworkData.Success(upcomingMovie)
            },{error ->
                Log.d("UpcomingMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllUpcomingMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({upcoming ->
                        upcomingMovieResponse.value = NetworkData.Success(upcoming)
                    },{
                        upcomingMovieResponse.value = NetworkData.Error(
                            error = ApiError(
                                status_code = (it as HttpException).code() ?: -1,
                                status_message = (it as HttpException).message ?: "Unknown Message",
                                success = false
                            )
                        )
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return upcomingMovieResponse
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}