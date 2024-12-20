package com.example.movie.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenresResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val genres: List<Genre>
)