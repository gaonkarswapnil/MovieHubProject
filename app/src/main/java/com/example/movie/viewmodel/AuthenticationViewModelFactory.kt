package com.example.movie.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movie.repository.apiimplementation.AuthenticationRepositoryImplementation

class AuthenticationViewModelFactory(
    val application: Application,
    val authencationRepository: AuthenticationRepositoryImplementation
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthenticationViewModel::class.java)){
            return AuthenticationViewModel(application, authencationRepository) as T
        }
        throw IllegalArgumentException("Unknown Exception")
    }
}