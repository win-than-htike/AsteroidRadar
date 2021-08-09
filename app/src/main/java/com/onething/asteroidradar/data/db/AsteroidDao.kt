package com.onething.asteroidradar.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onething.asteroidradar.domain.model.Asteroid
import com.onething.asteroidradar.domain.model.PictureOfDay

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM picture_of_day")
    fun getPictureOfDay(): LiveData<PictureOfDay?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(day: PictureOfDay)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroid(data: List<Asteroid>)

    @Query("SELECT * FROM asteroid WHERE closeApproachDate >= :startDate AND closeApproachDate <= :endDate ORDER BY closeApproachDate ASC")
    fun getAsteroidWithClosetDate(startDate: String, endDate: String): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate ASC")
    fun getAllSavedAsteroids(): LiveData<List<Asteroid>>

    @Query("DELETE FROM asteroid WHERE closeApproachDate < :today")
    fun deleteAsteroidDataBeyondToday(today: String): Int

}