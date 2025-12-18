package com.patrician.weather.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patrician.weather.ui.forecast.ForecastInputScreen
import com.patrician.weather.ui.forecast.ForecastScreen
import com.patrician.weather.ui.forecast.ForecastViewModel

// ホーム画面と天気画面を接続するナビゲーションホスト。
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route,
        modifier = modifier
    ) {
        composable(route = Routes.Home.route) {
            ForecastInputScreen(
                onNavigateToForecast = { city, lat, lon ->
                    val route = when {
                        city != null && city.isNotBlank() -> Routes.Forecast.createRoute(city)
                        lat != null && lon != null -> Routes.Forecast.createRoute(lat, lon)
                        else -> null
                    }
                    route?.let { navController.navigate(it) }
                }
            )
        }

        composable(
            route = Routes.Forecast.route,
            arguments = Routes.Forecast.args
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString(Routes.Forecast.ARG_CITY).orEmpty()
            val latString = backStackEntry.arguments?.getString(Routes.Forecast.ARG_LAT)
            val lonString = backStackEntry.arguments?.getString(Routes.Forecast.ARG_LON)

            val viewModel: ForecastViewModel = hiltViewModel()

            ForecastScreen(
                viewModel = viewModel,
                city = city,
                lat = latString?.toDoubleOrNull(),
                lon = lonString?.toDoubleOrNull(),
                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}
