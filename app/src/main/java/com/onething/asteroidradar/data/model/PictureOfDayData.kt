package com.onething.asteroidradar.data.model

import com.squareup.moshi.Json

data class PictureOfDayData(
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "url")
    val url: String,
    @field:Json(name = "media_type")
    val media_type: String
)