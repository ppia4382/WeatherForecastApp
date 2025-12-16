package com.patrician.weather.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room エンティティ:
 * - 都市または現在地のキーで識別
 * - JST 日付ごとにキャッシュを保持
 */
@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "location_key")
    val locationKey: String, // "city:東京" or "geo:lat=...,lon=..."
    val dateJst: String, // 例 "2025-12-17"
    val dt: Long, // UTC timestamp
    val temp: Double, // °C
    val icon: String // weather icon code
)