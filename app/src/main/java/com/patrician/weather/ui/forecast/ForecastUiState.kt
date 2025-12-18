package com.patrician.weather.ui.forecast

import com.patrician.weather.data.local.entity.ForecastEntity

/**
 * UI 状態:
 * - 読み込み中
 * - 成功（予報リスト）
 * - エラー
 */
sealed interface ForecastUiState {
    object Loading: ForecastUiState
    data class Success (val forecasts: List<ForecastEntity>): ForecastUiState
    object Error: ForecastUiState
}