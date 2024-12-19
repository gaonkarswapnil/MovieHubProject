package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.repository.apiimplementation.GenreRepositoryImplementation

class GenreViewModelFactory(
    val application: Application,
    val genreRepository: GenreRepositoryImplementation
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GenreViewModel::class.java)){
            return GenreViewModel(application, genreRepository) as T
        }
        throw IllegalArgumentException("Unknown Argument")
    }

}