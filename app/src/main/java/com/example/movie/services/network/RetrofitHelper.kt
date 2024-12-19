package com.example.movie.services.network

import com.example.movie.BuildConfig
import com.example.movie.model.GenresResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    private val BASE_URL = "https://api.themoviedb.org/3/"

    private val apiKey = BuildConfig.API_KEY

    private val retrofitHelper by lazy{
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val headerInterceptor = okhttp3.Interceptor{ chain ->
            val originalRequest = chain.request()
            val requestWithAuth = originalRequest.newBuilder()
                .addHeader("Authorization","Bearer ${apiKey}")
                .build()
            chain.proceed(requestWithAuth)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headerInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    fun getMovieList(): MovieListApi{
        return retrofitHelper.create(MovieListApi::class.java)
    }

    fun getDiscover(): DiscoverAPI{
        return retrofitHelper.create(DiscoverAPI::class.java)
    }

    fun getTrending(): TrendingAPI{
        return retrofitHelper.create(TrendingAPI::class.java)
    }

    fun getGenres(): GenreAPI{
        return retrofitHelper.create(GenreAPI::class.java)
    }

    fun getMovies(): MoviesAPI{
        return retrofitHelper.create(MoviesAPI::class.java)
    }

    fun getAuthentication(): Authentication{
        return retrofitHelper.create(Authentication::class.java)
    }
}