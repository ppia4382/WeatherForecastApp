package com.patrician.weather.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.patrician.weather.data.local.dao.ForecastDao
import com.patrician.weather.data.local.entity.ForecastEntity
import com.patrician.weather.data.remote.RetrofitProvider
import com.patrician.weather.util.TimeUtil

/**
 * Repository:
 * - キャッシュ優先
 * - 同日 JST の場合はキャッシュを返す
 * - それ以外は API を呼び出し、DB に保存して返す
 */
class ForecastRepository(
    private val dao: ForecastDao
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getForecastByCity(city: String?): List<ForecastEntity> {
        val key = "city:$city"
        val date = TimeUtil.todayJst()

        val cached = dao.getForecast(key, date)
        if (cached.isNotEmpty()) return cached

        return try {
            Log.d("ForecastRepository", "Cache empty. Fetching from network for city: $city")
            val response = RetrofitProvider.api.getFiveDayForecastByCity(city)
            val entities = response.list.map {
                ForecastEntity(
                    locationKey = key,
                    dateJst = date,
                    dt = it.dt,
                    temp = it.main.temp,
                    icon = it.weather.firstOrNull()?.icon ?: ""
                )
            }
            dao.clearForecast(key)
            dao.insertForecast(entities)
            entities
        } catch (e: Exception){
            Log.e("ForecastRepository", "Network call failed for city: $city", e)
            // ネットワーク失敗時はキャッシュを返す
            cached
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getForecastByCoordinates(lat: Double, lon: Double): List<ForecastEntity>{
        val key = "coords:$lat,$lon"
        val today = TimeUtil.todayJst()

        val cached = dao.getForecast(key, today)
        if (cached.isNotEmpty()) return cached

        return try {
            Log.d("ForecastRepository", "Cache empty. Fetching from network for coords:$lat,$lon")
            val response = RetrofitProvider.api.getFiveDayForecastByCoordinates(
                latitude = lat,
                longitude = lon
            )
            val entities = response.list.map {
                ForecastEntity(
                    locationKey = key,
                    dateJst = today,
                    dt = it.dt,
                    temp = it.main.temp,
                    icon = it.weather.firstOrNull()?.icon ?: ""
                )
            }
            dao.clearForecast(key)
            dao.insertForecast(entities)
            entities
        } catch (e: Exception) {
            Log.e("ForecastRepository", "Network call failed for coords:$lat,$lon", e)
            cached
        }
    }
}