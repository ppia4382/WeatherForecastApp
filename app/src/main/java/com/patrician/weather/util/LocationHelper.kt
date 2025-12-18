package com.patrician.weather.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await

/**
 * 端末の現在地（緯度・経度）を取得するためのヘルパークラス。
 * Google Play ServicesのFused Location Providerを使用して、最適化された位置情報を
 */
class LocationHelper(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    /**
     * 最新の現在地を取得します。
     * 権限チェックは呼び出し側（UI層）で行われることを想定していますが、
     * 安全性のためにSecurityExceptionをハンドリングしています。
     */
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): android.location.Location? {
        return try {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()
        } catch (e: SecurityException) {
            Log.e("LocationHelper", "Permission was revoked before call", e)
            null
        }catch (e: Exception) {
            Log.e("LocationHelper", "Failed to get location", e)
            null
        }
    }
}
