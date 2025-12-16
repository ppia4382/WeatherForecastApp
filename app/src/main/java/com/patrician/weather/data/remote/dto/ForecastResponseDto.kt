package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json

data class ForecastResponseDto(
    @Json(name = "list") val list: List<ForecastItemDto>,
    @Json(name = "city") val city: CityDto
)