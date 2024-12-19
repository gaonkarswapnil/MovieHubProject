package com.example.movie.repository.apiimplementation

import com.example.movie.model.CreateSessionLoginRequest
import com.example.movie.model.CreateSessionRequest
import com.example.movie.model.CreateSessionResponse
import com.example.movie.model.DeleteSessionResponse
import com.example.movie.model.RequestTokenResponse
import com.example.movie.repository.interfaces.AuthenticationRepository
import com.example.movie.services.network.RetrofitHelper
import io.reactivex.rxjava3.core.Observable

class AuthenticationRepositoryImplementation: AuthenticationRepository {
    override fun getRequestToken(): Observable<RequestTokenResponse>{
        return RetrofitHelper.getAuthentication().requestToken()
    }

    override fun createSessionViaLogin(sessionRequest: CreateSessionLoginRequest): Observable<RequestTokenResponse>{
        return RetrofitHelper.getAuthentication().createSessionLogin(sessionRequest)
    }

    override fun createSessionResponse(createSessionRequest: CreateSessionRequest): Observable<CreateSessionResponse>{
        return RetrofitHelper.getAuthentication().createSession(createSessionRequest)
    }

    override fun deleteSession(sessionId: String): Observable<DeleteSessionResponse>{
        return RetrofitHelper.getAuthentication().deleteSession(sessionId)
    }

}