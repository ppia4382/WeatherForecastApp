package com.patrician.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.patrician.weather.data.local.entity.ForecastEntity

/**
 * DAO: キャッシュの読み書きを行う
 */
@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecast WHERE location_key = :locationKey AND dateJst = :dateJst")
    suspend fun getForecast(locationKey: String, dateJst: String): List<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecasts: List<ForecastEntity>)

    @Query("DELETE FROM forecast WHERE location_key = :locationKey")
    suspend fun clearForecast(locationKey: String)


}