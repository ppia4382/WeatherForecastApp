package com.patrician.weather.ui.home

import com.patrician.weather.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCitySelected: (String) -> Unit
) {
    val cities =  mapOf(
        "Tokyo" to "東京",
        "Hyogo" to "兵庫",
        "Oita" to "大分",
        "Hokkaido" to "北海道"
    ) //listOf("東京", "兵庫", "大分", "北海道")

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.home_title))}) },
    ) { padding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            items(cities.toList()) { (englishName, japaneseName) ->
                Text(
                    text = japaneseName,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCitySelected(englishName) }
                        .padding(12.dp)
                )
            }
        }
    }
}