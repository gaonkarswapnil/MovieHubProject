package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.example.movie.model.ProductionCompany
import com.example.movie.model.ProductionCountry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductionCompanyConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromProductionCompanyList(value: List<ProductionCompany>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProductionCompanyList(value: String): List<ProductionCompany> {
        val type = object : TypeToken<List<ProductionCompany>>() {}.type
        return gson.fromJson(value, type)
    }
}