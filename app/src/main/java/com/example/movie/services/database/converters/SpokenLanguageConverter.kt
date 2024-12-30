package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.example.movie.model.SpokenLanguage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SpokenLanguageConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSpokenLanguageList(value: List<SpokenLanguage>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSpokenLanguageList(value: String): List<SpokenLanguage> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}