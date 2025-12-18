package com.patrician.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * アプリケーション全体で Hilt を有効化する。
 */
@HiltAndroidApp
class WeatherApp: Application()