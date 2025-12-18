package com.patrician.weather.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

/**
 * アプリケーションのナビゲーションルートを定義するクラス。
 *
 * 各画面のベースパス、遷移時に必要な引数の定義、および引数を含んだ
 * 遷移用パスを生成するヘルパー関数を一元管理します。
 */
sealed class Routes(val route: String) {
    // 検索入力を行うホーム画面
    object Home : Routes("home")
    // 天気予報を表示する詳細画面
    object Forecast : Routes("forecast?city={city}&lat={lat}&lon={lon}") {
        const val ARG_CITY = "city"
        const val ARG_LAT = "lat"
        const val ARG_LON = "lon"

        val args = listOf(
            navArgument(ARG_CITY) {
                type = NavType.StringType
                nullable = true
            },
            navArgument(ARG_LAT) {
                type = NavType.StringType
                nullable = true
            },
            navArgument(ARG_LON) {
                type = NavType.StringType
                nullable = true
            }
        )

        fun createRoute(city: String): String {
            //スペースを処理するために都市名を URL エンコードします（例: "Hokkaido"）。
            return "forecast?city=${android.net.Uri.encode(city)}"
        }

        fun createRoute(lat: Double, lon: Double): String {
            return "forecast?lat=$lat&lon=$lon"
        }
    }
}
