package com.patrician.weather.data.remote.api

import com.patrician.weather.BuildConfig
import com.patrician.weather.data.remote.dto.ForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("data/2.5/forecast")
    suspend fun getFiveDayForecastByCity(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ja",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): ForecastResponseDto

    @GET("data/2.5/forecast")
    suspend fun getFiveDayForecastByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ja",
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): ForecastResponseDto
}