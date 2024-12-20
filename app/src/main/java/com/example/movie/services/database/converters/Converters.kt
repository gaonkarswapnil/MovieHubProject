package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.example.movie.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromResultList(value: List<Result>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toResultList(value: String): List<Result> {
        val type = object : TypeToken<List<Result>>() {}.type
        return gson.fromJson(value, type)
    }





}