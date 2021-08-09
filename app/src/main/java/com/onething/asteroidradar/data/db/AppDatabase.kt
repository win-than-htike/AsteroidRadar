package com.onething.asteroidradar.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.onething.asteroidradar.domain.model.Asteroid
import com.onething.asteroidradar.domain.model.PictureOfDay
import com.onething.asteroidradar.utils.Converters

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}
