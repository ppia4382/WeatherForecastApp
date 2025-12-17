package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponseDto(
    @Json(name = "list") val list: List<ForecastItemDto>,
    @Json(name = "city") val city: CityDto
)