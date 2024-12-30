package com.example.movie.services.database.converters

import androidx.room.TypeConverter
import com.example.movie.model.BelongsToCollection
import com.example.movie.model.Dates
import com.example.movie.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BelongConverter {
    private val gson = Gson()

    // Converts a BelongsToCollection object to a JSON string
    @TypeConverter
    fun fromBelongsToCollection(value: BelongsToCollection?): String? {
        return gson.toJson(value)
    }

    // Converts a JSON string back into a BelongsToCollection object
    @TypeConverter
    fun toBelongsToCollection(value: String?): BelongsToCollection? {
        return gson.fromJson(value, BelongsToCollection::class.java)
    }

    // If you need to handle lists of BelongsToCollection, you can add the following:
    @TypeConverter
    fun fromBelongsToCollectionList(value: List<BelongsToCollection>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toBelongsToCollectionList(value: String?): List<BelongsToCollection>? {
        val type = object : TypeToken<List<BelongsToCollection>>() {}.type
        return gson.fromJson(value, type)
    }
}