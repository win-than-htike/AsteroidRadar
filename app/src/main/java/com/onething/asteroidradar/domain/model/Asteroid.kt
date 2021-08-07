package com.onething.asteroidradar.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid")
data class Asteroid(@PrimaryKey val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean)