package com.example.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discover_movie")
data class DiscoverMovieResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val page: Int,
    val results: List<Result>,
    val totalPages: Int,
    val totalResults: Int
)