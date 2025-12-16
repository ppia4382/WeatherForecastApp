package com.patrician.weather.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.ZoneId

/**
 * JST の日付を取得するユーティリティ
 */
object TimeUtil {
    @RequiresApi(Build.VERSION_CODES.O)
    fun todayJst(): String = LocalDate.now(ZoneId.of("Asia/Tokyo")).toString()
}