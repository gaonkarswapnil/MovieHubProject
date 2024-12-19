package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.repository.interfaces.MoviesRepository

class MoviesViewModelFactory(
    val application: Application,
    val moviesRepository: MoviesRepository
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesViewModel::class.java)){
            return MoviesViewModel(application, moviesRepository) as T
        }
        throw IllegalArgumentException("Unknown Argument")
    }
}