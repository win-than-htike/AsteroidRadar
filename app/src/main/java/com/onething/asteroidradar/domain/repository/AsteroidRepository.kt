package com.onething.asteroidradar.domain.repository

import androidx.lifecycle.LiveData
import com.onething.asteroidradar.domain.model.Asteroid
import com.onething.asteroidradar.domain.model.PictureOfDay
import com.onething.asteroidradar.utils.*
import kotlinx.coroutines.flow.Flow

interface AsteroidRepository {

    suspend fun saveAllAsteroidData(asteroid: List<Asteroid>)

    fun getAsteroidWithClosetDate(startDate: String, endDate: String): LiveData<List<Asteroid>>

    fun getAllSavedAsteroid(): LiveData<List<Asteroid>>

    suspend fun fetchAsteroidData(
        startDate: String = getToday(),
        endDate: String = getSevenDayTodayOnwards()
    ): Either<DataException, List<Asteroid>>

    suspend fun deleteAsteroidBeyondToday(date: String = getToday())

    suspend fun fetchImageOfTheDay(): Either<DataException, PictureOfDay>

    fun getPictureOfDay(): LiveData<PictureOfDay?>

    suspend fun savePictureOfDay(pictureOfDay: PictureOfDay)

}