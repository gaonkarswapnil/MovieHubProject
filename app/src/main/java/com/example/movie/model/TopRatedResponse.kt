package com.example.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_rated_movies")
data class TopRatedResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val page: Int,
    val results: List<Result>,
    val totalPages: Int,
    val totalResults: Int
)