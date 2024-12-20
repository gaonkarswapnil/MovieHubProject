package com.example.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies")
data class PopularResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)