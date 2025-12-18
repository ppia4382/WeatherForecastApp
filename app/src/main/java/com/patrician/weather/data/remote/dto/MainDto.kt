package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainDto(
    @Json(name = "temp") val temp: Double, //°C (metric)
)