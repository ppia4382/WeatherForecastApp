package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json

data class MainDto(
    @Json(name = "temp") val temp: Double, //°C (metric)
)