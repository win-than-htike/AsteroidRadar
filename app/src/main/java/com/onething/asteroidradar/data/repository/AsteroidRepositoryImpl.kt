package com.onething.asteroidradar.data.repository

import androidx.lifecycle.LiveData
import com.onething.asteroidradar.data.db.AsteroidDao
import com.onething.asteroidradar.data.network.ApiService
import com.onething.asteroidradar.domain.mapper.AsteroidMapper.toDomain
import com.onething.asteroidradar.domain.model.Asteroid
import com.onething.asteroidradar.domain.model.PictureOfDay
import com.onething.asteroidradar.domain.repository.AsteroidRepository
import com.onething.asteroidradar.utils.DataException
import com.onething.asteroidradar.utils.Either
import com.onething.asteroidradar.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class AsteroidRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val asteroidDao: AsteroidDao,
) : AsteroidRepository {

    override suspend fun fetchAsteroidData(startDate: String, endDate: String): Either<DataException, List<Asteroid>> {
        return try {
            val response = apiService.fetchAsteroidData(startDate, endDate)
            val result = JSONObject(response).toDomain()
            saveAllAsteroidData(result)
            Either.Right(result)
        } catch (e: Exception) {
            Either.Left(DataException.Api(e.localizedMessage ?: "Can't fetch data."))
        }
    }

    override suspend fun saveAllAsteroidData(asteroid: List<Asteroid>) {
        withContext(Dispatchers.IO) {
            asteroidDao.insertAllAsteroid(asteroid)
        }
    }

    override fun getAsteroidWithClosetDate(startDate: String, endDate: String): LiveData<List<Asteroid>> {
        return asteroidDao.getAsteroidWithClosetDate(startDate, endDate)
    }

    override fun getAllSavedAsteroid(): LiveData<List<Asteroid>> {
        return asteroidDao.getAllSavedAsteroids()
    }

    override suspend fun deleteAsteroidBeyondToday(date: String) {
        asteroidDao.deleteAsteroidDataBeyondToday(date)
    }

    override suspend fun fetchImageOfTheDay(): Either<DataException, PictureOfDay> {
        return try {
            val data = apiService.fetchImageOfTheDay()
            savePictureOfDay(data.toDomain())
            Either.Right(data.toDomain())
        } catch (e: Exception) {
            Either.Left(DataException.Api(e.localizedMessage ?: "Can't fetch data."))
        }
    }

    override fun getPictureOfDay(): LiveData<PictureOfDay?> = asteroidDao.getPictureOfDay()

    override suspend fun savePictureOfDay(pictureOfDay: PictureOfDay) =
        withContext(Dispatchers.IO) {
            asteroidDao.insertPictureOfDay(pictureOfDay)
        }

}