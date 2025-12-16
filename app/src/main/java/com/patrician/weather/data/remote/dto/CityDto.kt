package com.patrician.weather.data.remote.dto

import com.squareup.moshi.Json

class CityDto(
    @Json(name = "name") val name: String
)