package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json

data class WeatherDto(
    @Json(name = "icon") val icon: String, //天気アイコン
    @Json(name = "description") val description: String? = null //天気の説明
)
