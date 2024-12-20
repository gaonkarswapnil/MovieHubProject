package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.example.movie.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GenreConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromGenreList(value: List<Genre>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toGenreList(value: String): List<Genre> {
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(value, type)
    }
}