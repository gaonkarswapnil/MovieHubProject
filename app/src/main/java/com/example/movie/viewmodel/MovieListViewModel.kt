package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.NowPlayingResponse
import com.example.movie.model.PopularResponse
import com.example.movie.model.TopRatedResponse
import com.example.movie.model.UpcomingMovieResponse
import com.example.movie.repository.interfaces.MovieListRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieListViewModel(
    val application: Application,
    val movieListRepository: MovieListRepository
): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun popularMovie(): LiveData<PopularResponse>{
        val popularResponse = MutableLiveData<PopularResponse>()

        movieListRepository.popularMovie()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({popularMovie ->
                popularResponse.value = popularMovie
            }, {error ->
                Log.d("PopularMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllPopulatMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({popular ->
                        popularResponse.value = popular
                    },{
                        it.printStackTrace()
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return popularResponse
    }

    fun nowPlaying(): LiveData<NowPlayingResponse>{
        val nowPlayingResponse = MutableLiveData<NowPlayingResponse>()

        movieListRepository.nowPlaying()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({nowPlaying ->
                nowPlayingResponse.value = nowPlaying
            },{error ->
                Log.d("NowPlayingMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllNowPlayingFromDB()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({nowPlaying ->
                        nowPlayingResponse.value = nowPlaying
                    },{
                        it.printStackTrace()
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return nowPlayingResponse
    }

    fun topRatedMovies(): LiveData<TopRatedResponse>{
        val topRatedResponse = MutableLiveData<TopRatedResponse>()

        movieListRepository.topRatedMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({topRatedMovie ->
                topRatedResponse.value = topRatedMovie
            },{error ->
                Log.d("TopRatedMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllTopRatedMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({topRated ->
                        topRatedResponse.value = topRated
                    },{
                        it.printStackTrace()
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return topRatedResponse
    }

    fun upcomingMovies(): LiveData<UpcomingMovieResponse>{
        val upcomingMovieResponse = MutableLiveData<UpcomingMovieResponse>()

        movieListRepository.upcomingMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({upcomingMovie ->
                upcomingMovieResponse.value = upcomingMovie
            },{error ->
                Log.d("UpcomingMovieList", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
                movieListRepository.getAllUpcomingMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({upcoming ->
                        upcomingMovieResponse.value = upcoming
                    },{
                        it.printStackTrace()
                    }).addTo(compositeDisposable)
            }).addTo(compositeDisposable)

        return upcomingMovieResponse
    }
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}