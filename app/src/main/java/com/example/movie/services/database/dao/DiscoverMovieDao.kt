package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.DiscoverMovieResponse
import com.example.movie.model.GenresResponse

@Dao
interface DiscoverMovieDao {
    @Query("SELECT * FROM discover_movie LIMIT 1")
    fun getAllDiscoverMovie(): DiscoverMovieResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiscoverMovie(movies: DiscoverMovieResponse)

    @Query("DELETE FROM discover_movie")
    fun deleteAllDiscoverMovie()
}