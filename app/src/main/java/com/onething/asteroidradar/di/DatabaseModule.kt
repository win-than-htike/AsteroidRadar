package com.onething.asteroidradar.di

import android.content.Context
import androidx.room.Room
import com.onething.asteroidradar.data.db.AppDatabase
import com.onething.asteroidradar.data.db.AsteroidDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext application: Context,
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "Asteroid.db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideAsteroidDao(appDatabase: AppDatabase): AsteroidDao {
        return appDatabase.asteroidDao
    }
}