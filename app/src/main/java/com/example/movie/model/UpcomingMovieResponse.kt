package com.example.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upcoming_movies")
data class UpcomingMovieResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)