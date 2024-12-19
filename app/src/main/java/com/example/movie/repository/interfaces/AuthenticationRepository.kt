package com.example.movie.repository.interfaces

import com.example.movie.model.CreateSessionLoginRequest
import com.example.movie.model.CreateSessionRequest
import com.example.movie.model.CreateSessionResponse
import com.example.movie.model.DeleteSessionResponse
import com.example.movie.model.RequestTokenResponse
import io.reactivex.rxjava3.core.Observable

interface AuthenticationRepository {
    fun getRequestToken(): Observable<RequestTokenResponse>

    fun createSessionViaLogin(sessionRequest: CreateSessionLoginRequest): Observable<RequestTokenResponse>

    fun createSessionResponse(createSessionRequest: CreateSessionRequest): Observable<CreateSessionResponse>

    fun deleteSession(sessionId: String): Observable<DeleteSessionResponse>
}