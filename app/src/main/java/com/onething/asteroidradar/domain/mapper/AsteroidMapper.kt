package com.onething.asteroidradar.domain.mapper

import com.onething.asteroidradar.data.model.PictureOfDayData
import com.onething.asteroidradar.domain.model.Asteroid
import com.onething.asteroidradar.domain.model.PictureOfDay
import com.onething.asteroidradar.utils.getNextSevenDaysFormattedDates
import org.json.JSONObject
import timber.log.Timber

object AsteroidMapper {

    fun JSONObject.toDomain(): List<Asteroid> {
        try {
            val asteroidList = ArrayList<Asteroid>()
            val nearEarthObjectsJson = getJSONObject("near_earth_objects")
            val nextSevenDaysFormattedDates = getNextSevenDaysFormattedDates()
            for (formattedDate in nextSevenDaysFormattedDates) {
                if (nearEarthObjectsJson.has(formattedDate)) {
                    val dateAsteroidJsonArray = nearEarthObjectsJson.getJSONArray(formattedDate)
                    Timber.d("Json Parse Data $dateAsteroidJsonArray")
                    for (i in 0 until dateAsteroidJsonArray.length()) {
                        val asteroidJson = dateAsteroidJsonArray.getJSONObject(i)
                        val id = asteroidJson.getLong("id")
                        val codename = asteroidJson.getString("name")
                        val absoluteMagnitude = asteroidJson.getDouble("absolute_magnitude_h")
                        val estimatedDiameter = asteroidJson.getJSONObject("estimated_diameter")
                            .getJSONObject("kilometers").getDouble("estimated_diameter_max")
                        val closeApproachData = asteroidJson
                            .getJSONArray("close_approach_data").getJSONObject(0)
                        val relativeVelocity = closeApproachData.getJSONObject("relative_velocity")
                            .getDouble("kilometers_per_second")
                        val distanceFromEarth = closeApproachData.getJSONObject("miss_distance")
                            .getDouble("astronomical")
                        val isPotentiallyHazardous = asteroidJson
                            .getBoolean("is_potentially_hazardous_asteroid")

                        val asteroid = Asteroid(
                            id,
                            codename,
                            formattedDate,
                            absoluteMagnitude,
                            estimatedDiameter,
                            relativeVelocity,
                            distanceFromEarth,
                            isPotentiallyHazardous
                        )
                        asteroidList.add(asteroid)
                    }
                }
            }
            return asteroidList
        } catch (e: Exception) {
            Timber.d("Json Parse Error ${e.printStackTrace()}")
            e.printStackTrace()
        }

        return emptyList()
    }

    fun PictureOfDayData.toDomain(): PictureOfDay {
        return PictureOfDay(
            url = url,
            title = title,
            mediaType = media_type
        )
    }

}