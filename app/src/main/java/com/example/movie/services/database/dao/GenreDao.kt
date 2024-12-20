package com.example.movie.services.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie.model.GenresResponse

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    fun getAllGenre(): GenresResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenre(movies: GenresResponse)

    @Query("DELETE FROM genres")
    fun deleteAllGenre()
}