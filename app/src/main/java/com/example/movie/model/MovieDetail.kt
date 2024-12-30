package com.example.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetail (
    @PrimaryKey(autoGenerate = true)
    val movieId: Int = 0,

    val adult: Boolean,
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val title: String,
    val video: Boolean,

    val flag: Boolean = false,
    val isSync: Boolean = false
)