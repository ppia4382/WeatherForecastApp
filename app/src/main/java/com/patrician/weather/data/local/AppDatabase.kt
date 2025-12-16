package com.patrician.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.patrician.weather.data.local.dao.ForecastDao
import com.patrician.weather.data.local.entity.ForecastEntity

/**
 * Room データベース: ForecastEntity を保持
 */
@Database(entities = [ForecastEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun forecastDao(): ForecastDao
}