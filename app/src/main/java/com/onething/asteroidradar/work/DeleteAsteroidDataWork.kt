package com.onething.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.onething.asteroidradar.domain.repository.AsteroidRepository
import timber.log.Timber
import javax.inject.Inject

class DeleteAsteroidDataWork @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val repository: AsteroidRepository
) : CoroutineWorker(context, params) {

    companion object {
        const val WORK_NAME = "DeleteAsteroidWork"
    }

    override suspend fun doWork(): Result {
        Timber.d("DO WORK SUCCESS DeleteAsteroidWork")
        return try {
            repository.deleteAsteroidBeyondToday()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

}