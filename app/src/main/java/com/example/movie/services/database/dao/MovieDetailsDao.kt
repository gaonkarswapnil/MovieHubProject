package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.GenresResponse
import com.example.movie.model.MovieDetail
import com.example.movie.model.MovieDetailsResponse
import retrofit2.http.DELETE

@Dao
interface MovieDetailsDao {
    @Query("SELECT * FROM movie_details")
    fun getAllMovieDetails(): MovieDetail

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movies: MovieDetail)

    @Query("DELETE FROM movie_details where id = :movieId")
    suspend fun deleteAllMovies(movieId: Int)
}