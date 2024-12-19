package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.repository.interfaces.TrendingRepository

class TrendingViewModelFactory(
    val application: Application,
    val trendingRepository: TrendingRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TrendingViewModel::class.java)){
            return TrendingViewModel(application, trendingRepository) as T
        }
        throw IllegalArgumentException("Unknown Argument")
    }

}