package com.onething.asteroidradar.di

import com.onething.asteroidradar.data.repository.AsteroidRepositoryImpl
import com.onething.asteroidradar.domain.repository.AsteroidRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAsteroidRepository(
        repositoryImpl: AsteroidRepositoryImpl
    ): AsteroidRepository

}