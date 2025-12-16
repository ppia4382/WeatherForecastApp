package com.patrician.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patrician.weather.ui.forecast.ForecastScreen
import com.patrician.weather.ui.home.HomeScreen

// ホーム画面と天気画面を接続するナビゲーションホスト。
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        modifier = modifier
    ){
        composable(Routes.Home.route) {
            HomeScreen(onCitySelected = { city ->
                navController.navigate(Routes.Forecast.createRoute(city))
            })
        }
        composable(
            route = Routes.Forecast.route,
            arguments = Routes.Forecast.args
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString(Routes.Forecast.argName)
            city?.let {
                ForecastScreen(city = it)
            }
        }

    }
}
