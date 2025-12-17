package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CityDto(
    @Json(name = "name") val name: String
)