package com.example.movie.services.network

import com.example.movie.model.CreateSessionLoginRequest
import com.example.movie.model.CreateSessionRequest
import com.example.movie.model.CreateSessionResponse
import com.example.movie.model.DeleteSessionRequest
import com.example.movie.model.DeleteSessionResponse
import com.example.movie.model.RequestTokenResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Authentication {
    @GET("authentication/token/new")
    fun requestToken(): Observable<RequestTokenResponse>

    @POST("authentication/token/validate_with_login")
    fun createSessionLogin(
        @Body sessionRequest: CreateSessionLoginRequest
    ): Observable<RequestTokenResponse>

    @POST("authentication/session/new")
    fun createSession(
        @Body createSessionRequest: CreateSessionRequest
    ): Observable<CreateSessionResponse>

    @DELETE("authentication/session")
    fun deleteSession(
        @Query("session_id") sessionId: String
    ): Observable<DeleteSessionResponse>
}