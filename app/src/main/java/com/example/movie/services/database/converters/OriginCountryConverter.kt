package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class OriginCountryConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromOriginCountryList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toOriginCountryList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}