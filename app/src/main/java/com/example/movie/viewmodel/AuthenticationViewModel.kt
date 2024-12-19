package com.example.movie.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.model.CreateSessionLoginRequest
import com.example.movie.model.CreateSessionRequest
import com.example.movie.model.CreateSessionResponse
import com.example.movie.model.DeleteSessionResponse
import com.example.movie.model.RequestTokenResponse
import com.example.movie.repository.apiimplementation.AuthenticationRepositoryImplementation
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class AuthenticationViewModel(
    val application: Application,
    val authenticationRepository: AuthenticationRepositoryImplementation
):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun getRequestToken(): LiveData<RequestTokenResponse>{
        val requestTokenResponse = MutableLiveData<RequestTokenResponse>()

        authenticationRepository.getRequestToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({token ->
                if(token.success){
                    requestTokenResponse.value = token
                }else{

                }
            },{error ->
//                Log.d("Authentication", error.message.toString())
                Log.e("Authentication RequestToken", "Error: ${error.message}", error)
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
            }).addTo(compositeDisposable)

        return requestTokenResponse
    }

    fun createSessionViaLogin(sessionRequest: CreateSessionLoginRequest): LiveData<RequestTokenResponse>{
        val requestTokenResponse = MutableLiveData<RequestTokenResponse>()

        authenticationRepository.createSessionViaLogin(sessionRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response->
                requestTokenResponse.value = response
            },{error ->
                Log.d("Authentication", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
            }).addTo(compositeDisposable)

        return requestTokenResponse
    }

    fun createSessionResponse(createSessionRequest: CreateSessionRequest): LiveData<CreateSessionResponse>{
        val createSessionResponse = MutableLiveData<CreateSessionResponse>()

        authenticationRepository.createSessionResponse(createSessionRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                createSessionResponse.value = response
            },{error ->
                Log.d("Authentication", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
            }).addTo(compositeDisposable)

        return createSessionResponse
    }

    fun deleteSession(sessionId: String): LiveData<DeleteSessionResponse>{
        val deleteSessionResponse = MutableLiveData<DeleteSessionResponse>()

        authenticationRepository.deleteSession(sessionId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                deleteSessionResponse.value = response
            },{error ->
                Log.d("Authentication", "Error")
//                Toast.makeText(application, "ERROR", Toast.LENGTH_SHORT).show()
            }).addTo(compositeDisposable)

        return deleteSessionResponse
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}