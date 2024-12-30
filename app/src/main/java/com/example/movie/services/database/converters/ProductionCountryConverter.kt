package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.example.movie.model.ProductionCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCountryConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromProductionCountryList(value: List<ProductionCountry>?): String? {
        return gson.toJson(value)
    }

    // Converts a JSON string back into a List<ProductionCountry>
    @TypeConverter
    fun toProductionCountryList(value: String?): List<ProductionCountry>? {
        val gson = Gson()
        val type = object : TypeToken<List<ProductionCountry>>() {}.type
        return gson.fromJson(value, type)
    }
}