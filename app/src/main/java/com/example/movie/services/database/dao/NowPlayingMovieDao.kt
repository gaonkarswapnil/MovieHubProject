package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.NowPlayingResponse

@Dao
interface NowPlayingMovieDao {
    @Query("SELECT * FROM now_playing_movies")
    fun getAllNowPlayingMovies(): NowPlayingResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNowPlayingMovies(movies: NowPlayingResponse)

    @Query("DELETE FROM now_playing_movies")
    fun deleteAllNowPlayingMovies()
}