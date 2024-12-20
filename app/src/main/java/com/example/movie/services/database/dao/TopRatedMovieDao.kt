package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.TopRatedResponse

@Dao
interface TopRatedMovieDao {
    @Query("SELECT * FROM top_rated_movies")
    fun getAllTopRatedMovies(): TopRatedResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRatedMovies(movies: TopRatedResponse)

    @Query("DELETE FROM top_rated_movies")
    fun deleteAllTopRatedMovies()
}