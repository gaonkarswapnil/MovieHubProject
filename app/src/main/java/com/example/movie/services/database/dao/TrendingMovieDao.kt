package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.TrendingMovieResponse

@Dao
interface TrendingMovieDao {
    @Query("SELECT * FROM trending_movies")
    fun getAllTrendingMovies(): TrendingMovieResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrendingMovies(movies: TrendingMovieResponse)

    @Query("DELETE FROM trending_movies")
    fun deleteAllTrendingMovies()
}