package com.onething.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.onething.asteroidradar.domain.repository.AsteroidRepository
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class RefreshAsteroidDataWork @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val repository: AsteroidRepository
) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroidWork"
    }

    override suspend fun doWork(): Result {
        Timber.d("DO WORK SUCCESS RefreshAsteroidWork")
        return try {
            repository.fetchAsteroidData()
            repository.fetchImageOfTheDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}