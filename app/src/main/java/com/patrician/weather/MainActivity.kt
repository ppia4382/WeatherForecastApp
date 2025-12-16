package com.patrician.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.patrician.weather.ui.forecast.ForecastScreen
import com.patrician.weather.ui.home.HomeScreen
import com.patrician.weather.ui.navigation.AppNavHost
import com.patrician.weather.ui.theme.WeatherForecastAppTheme

class MainActivity : ComponentActivity() {
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
fun HomeScreenPreview() {
    WeatherForecastAppTheme {
        HomeScreen(
            onCitySelected = {},
        )
    }
}

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
