package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastItemDto(
    @Json(name = "dt") val dt: Long, //UTC epoch seconds
    @Json(name = "main") val main: MainDto, //温度
    @Json(name = "weather") val weather: List<WeatherDto>, //アイコン, 説明
)