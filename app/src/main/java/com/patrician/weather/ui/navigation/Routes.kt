package com.patrician.weather.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

// 画面遷移のルートを定義する sealed class。
// - ルート名を一元管理し、タイプセーフにする
// - ForecastScree は 「city」 引数を必要とする
sealed class Routes (val route: String) {
    data object  Home: Routes("home")
    data object Forecast: Routes("forecast/{city}"){
        const val argName = "city"
        val args: List<NamedNavArgument> = listOf(
            navArgument(argName) { type = NavType.StringType }
        )
        fun createRoute(city: String): String = "forecast/$city"
    }
}