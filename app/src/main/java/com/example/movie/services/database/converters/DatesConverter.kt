package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.example.movie.model.Dates
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DatesConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromDates(dates: Dates?): String {
        return gson.toJson(dates)
    }

    @TypeConverter
    fun toDates(datesJson: String?): Dates? {
        val type = object : TypeToken<Dates>() {}.type
        return gson.fromJson(datesJson, type)
    }
}