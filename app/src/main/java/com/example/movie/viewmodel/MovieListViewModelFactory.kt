package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.repository.interfaces.MovieListRepository

class MovieListViewModelFactory(
    val application: Application,
    val movieListRepository: MovieListRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieListViewModel::class.java)){
            return MovieListViewModel(application, movieListRepository) as T
        }
        throw IllegalArgumentException("Unknown Argument")
    }

}