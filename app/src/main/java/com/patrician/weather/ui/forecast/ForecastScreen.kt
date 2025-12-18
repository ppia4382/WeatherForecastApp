package com.patrician.weather.ui.forecast

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import com.patrician.weather.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.patrician.weather.data.local.entity.ForecastEntity
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object ApiConstants {
    const val OPEN_WEATHER_ICON_BASE_URL = "https://openweathermap.org/img/wn/"
}

/**
 * Forecast 画面：
 * - ViewModel の状態を監視
 * - 成功時は予報リスト表示
 * - エラー時はメッセージとリトライボタン
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen (
    city: String?,
    viewModel: ForecastViewModel,
    onNavigateUp: () -> Unit,
    lat: Double? = null,
    lon: Double? = null
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(city, lat, lon) {
        if (lat != null && lon != null) {
            viewModel.loadForecastByCoordinates(lat, lon)
        } else if (!city.isNullOrBlank()) {
            viewModel.loadForecast(city)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.forecast_title)) }) },
        containerColor = Color(0xFF13B0A3)
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            val headerText = if (city.isNullOrBlank() && lat != null && lon != null) {
                "緯度: $lat, 経度: $lon"
            } else {
                stringResource(R.string.selected_city, city ?: "")
            }
            Text(
                text = headerText,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (uiState) {
                    is ForecastUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is ForecastUiState.Success -> {
                        val forecasts = (uiState as ForecastUiState.Success).forecasts
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(forecasts) { forecast ->
                                ForecastRow(forecast)
                            }

                        }
                    }
                    is ForecastUiState.Error -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(stringResource(R.string.network_disconnected))
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.loadForecast(city) }) {
                                Text(stringResource(R.string.retry))
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastRow(
    item: ForecastEntity) {
    val formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm")
    val time = Instant.ofEpochSecond(item.dt)
        .atZone(ZoneId.of("Asia/Tokyo"))
        .format(formatter)
    val iconUrl = "${ApiConstants.OPEN_WEATHER_ICON_BASE_URL}${item.icon}@2x.png"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(time)
        Text(stringResource(R.string.temperature, item.temp ))
        Image(
            painter = rememberAsyncImagePainter(iconUrl),
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }
}