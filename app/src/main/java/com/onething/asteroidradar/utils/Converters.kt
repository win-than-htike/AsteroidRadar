package com.onething.asteroidradar.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onething.asteroidradar.domain.model.Asteroid
import java.lang.reflect.Type

object Converters {

    @TypeConverter
    fun fromString(value: String): ArrayList<Asteroid> {
        val listType: Type = object : TypeToken<ArrayList<Asteroid>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Asteroid>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

}