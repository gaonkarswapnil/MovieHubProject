package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.repository.interfaces.DiscoverRepository

class DiscoverViewModelFactory(
    val application: Application,
    val discoverRepository: DiscoverRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DiscoverViewModel::class.java)){
            return DiscoverViewModel(application, discoverRepository) as T
        }
        throw IllegalArgumentException("Unknown Argument")
    }

}