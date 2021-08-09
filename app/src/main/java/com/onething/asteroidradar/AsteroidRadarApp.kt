package com.onething.asteroidradar

import android.app.Application
import android.os.Build
import androidx.work.*
import com.onething.asteroidradar.work.DeleteAsteroidDataWork
import com.onething.asteroidradar.work.RefreshAsteroidDataWork
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class AsteroidRadarApp : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        val config = Configuration.Builder()
            .build()
        WorkManager.initialize(this, config)
        delayedInit()
    }

    private fun setupRecurringWork() {

        // delete work
        val constraintsDelete = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequestDelete =
            PeriodicWorkRequestBuilder<DeleteAsteroidDataWork>(1, TimeUnit.DAYS)
                .setConstraints(constraintsDelete)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            DeleteAsteroidDataWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequestDelete
        )

        // refresh work
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshAsteroidDataWork>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshAsteroidDataWork.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

}