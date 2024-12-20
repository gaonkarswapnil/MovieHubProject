package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.PopularResponse

@Dao
interface PopularMoviesDao {
    @Query("SELECT * FROM popular_movies")
    fun getAllPopularMovies(): PopularResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopularMovies(movies: PopularResponse)

    @Query("DELETE FROM popular_movies")
    fun deleteAllPopularMovies()
}