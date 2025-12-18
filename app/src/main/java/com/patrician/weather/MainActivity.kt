package com.patrician.weather

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.patrician.weather.ui.forecast.ForecastInputScreen
import com.patrician.weather.ui.navigation.AppNavHost
import com.patrician.weather.ui.theme.WeatherForecastAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(color = MaterialTheme.colorScheme.background){
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.fillMaxSize()
                    )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastInputScreenPreview() {
    WeatherForecastAppTheme {
        ForecastInputScreen(onNavigateToForecast = { _, _, _ -> })
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AppNavHostPreview() {
    WeatherForecastAppTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController,
            modifier = Modifier
        )
    }
}
