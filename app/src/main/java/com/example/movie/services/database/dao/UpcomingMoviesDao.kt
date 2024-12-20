package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.PopularResponse
import com.example.movie.model.UpcomingMovieResponse

@Dao
interface UpcomingMoviesDao {
    @Query("SELECT * FROM upcoming_movies")
    fun getAllUpcomingMovies(): UpcomingMovieResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpcomingMovies(movies: UpcomingMovieResponse)

    @Query("DELETE FROM upcoming_movies")
    fun deleteAllUpcomingMovies()
}