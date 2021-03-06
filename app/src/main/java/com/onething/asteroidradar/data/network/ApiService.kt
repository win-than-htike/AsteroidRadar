package com.onething.asteroidradar.data.network

import com.onething.asteroidradar.data.model.PictureOfDayData
import com.onething.asteroidradar.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("planetary/apod")
    suspend fun fetchImageOfTheDay(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): PictureOfDayData

    @GET("neo/rest/v1/feed")
    suspend fun fetchAsteroidData(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String

}