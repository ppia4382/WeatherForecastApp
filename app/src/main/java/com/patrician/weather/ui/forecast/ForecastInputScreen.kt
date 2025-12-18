package com.patrician.weather.ui.forecast

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.patrician.weather.R

private data class CityInfo(val lat: Double, val lon: Double)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastInputScreen(
    onNavigateToForecast: (city: String?, lat: Double?, lon: Double?) -> Unit
) {
    val brandColor = Color(0xFF13B0A3)

    var cityInput by remember { mutableStateOf("") }
    var latInput by remember { mutableStateOf("") }
    var lonInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val cities = remember {
        mapOf(
            "Tokyo" to CityInfo(35.6895, 139.6917),
            "Hyogo" to CityInfo(34.6913, 135.1831),
            "Oita" to CityInfo(33.2333, 131.6000),
            "Hokkaido" to CityInfo(43.2203, 142.8635)
        )
    }

    val handleGetForecast = {
        keyboardController?.hide()
        val trimmedCity = cityInput.trim()
        val lat = latInput.toDoubleOrNull()
        val lon = lonInput.toDoubleOrNull()

        when {
            lat != null && lon != null -> {
                errorMessage = null
                onNavigateToForecast(null, lat, lon)
            }
            trimmedCity.isNotBlank() -> {
                errorMessage = null
                onNavigateToForecast(trimmedCity, null, null)
            }
            else -> {
                errorMessage = "入力エラー"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.home_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = brandColor,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = cityInput,
                onValueChange = {
                    cityInput = it
                    if (it.isNotBlank()) { latInput = ""; lonInput = "" }
                },
                label = { Text(stringResource(R.string.input_select_city_label)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )

            Text(stringResource(R.string.input_or_coordinates))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = latInput,
                    onValueChange = {
                        latInput = it
                        if (it.isNotBlank()) cityInput = ""
                    },
                    label = { Text(stringResource(R.string.input_latitude_label)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
                )
                TextField(
                    modifier = Modifier.weight(1f),
                    value = lonInput,
                    onValueChange = {
                        lonInput = it
                        if (it.isNotBlank()) cityInput = ""
                    },
                    label = { Text(stringResource(R.string.input_longitude_label)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { handleGetForecast() })
                )
            }

            if (errorMessage != null) {
                Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = handleGetForecast,
                modifier = Modifier.fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(containerColor = brandColor)
            ) {
                Text(stringResource(R.string.input_get_forecast_button))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider()

        Text(
            text = stringResource(R.string.input_searchable_cities_title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        SelectionContainer {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(cities.entries.toList()) { entry ->
                    Surface(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = entry.key, fontWeight = FontWeight.Bold)
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${stringResource(R.string.input_label_lat)}${entry.value.lat}",
                                    color = brandColor,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "${stringResource(R.string.input_label_lon)}${entry.value.lon}",
                                    color = brandColor,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
