package com.patrician.weather.ui.forecast

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrician.weather.data.repository.ForecastRepository
import com.patrician.weather.debugFetchTokyo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel:
 * - Repository を呼び出し
 * - StateFlow でUI状態を公開
 */
@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel(){

    private val _uiState = MutableStateFlow<ForecastUiState>(ForecastUiState.Loading)
    val uiState: StateFlow<ForecastUiState> = _uiState

    //特定の画面を開く際にAPIが問題なく作動しているか確認する。
    init {
        viewModelScope.launch {
            try {
                // This is likely what is causing the 404 crash on startup
                debugFetchTokyo()
            } catch (e: Exception) {
                Log.e("ForecastViewModel", "Debug fetch failed, but preventing crash", e)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadForecast(city: String?) {
        _uiState.value = ForecastUiState.Loading
        viewModelScope.launch {

//            runCatching { repository.getForecastByCity(city) }
//                .onSuccess { _uiState.value = ForecastUiState.Success(it) }
//                .onFailure { _uiState.value = ForecastUiState.Error }

            try {
                // 1. Fetch the data
                val result = repository.getForecastByCity(city)

                // 2. Update UI to Success state
                _uiState.value = ForecastUiState.Success(result)

            } catch (e: retrofit2.HttpException) {
                // This catches the 404 (City Not Found) or other API errors
                Log.e("Weather", "HTTP Error: ${e.code()} ${e.message()}")
                _uiState.value = ForecastUiState.Error
            } catch (e: Exception) {
                // This catches Network issues or parsing errors
                Log.e("Weather", "Generic Error", e)
                _uiState.value = ForecastUiState.Error
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadForecastByCoordinates(lat: Double, lon: Double) {
        Log.d("ForecastViewModel", "Loading coordinates: lat=$lat, lon=$lon")
        _uiState.value = ForecastUiState.Loading
        viewModelScope.launch {
            runCatching { repository.getForecastByCoordinates(lat, lon) }
                .onSuccess {
                    Log.d("ForecastViewModel", "Success: ${it.size} items")
                    _uiState.value = ForecastUiState.Success(it)
                }
                .onFailure {
                    Log.e("ForecastViewModel", "Error loading coordinates", it)
                    _uiState.value = ForecastUiState.Error
                }
        }
    }
}