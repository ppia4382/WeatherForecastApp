package com.patrician.weather

import android.util.Log
import com.patrician.weather.data.remote.RetrofitProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * ApiSandbox クラスの目的:
 * - Retrofit クライアントと DTO が正しく動作するかを簡単に確認するための開発用ユーティリティ
 * - 実際の UI や Repository には組み込まれず、デバッグのために利用する
 * - コルーチン内で API を呼び出し、レスポンスの一部をログに出力することで、
 *   エンドポイントや JSON パースが正しく機能しているかを検証できる
 * - 本番コードには含めず、開発段階でのみ使用することを想定
 */
suspend fun debugFetchTokyo(){
    val response = withContext(Dispatchers.IO) {
        RetrofitProvider.api.getFiveDayForecastByCity("東京")
    }
    val first = response.list.firstOrNull()
    Log.d("ApiSandbox", "city=${response.city.name}, dt=${first?.dt}, temp=${first?.main?.temp}, icon=${first?.weather?.firstOrNull()?.icon}")
}