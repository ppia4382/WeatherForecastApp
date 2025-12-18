# WeatherForecastApp

## セットアップ手順
このプロジェクトを実行するには、OpenWeatherMap の API キーが必要です。
1.  [OpenWeatherMap](https://openweathermap.org/api) で無料の API キーを取得します。
2.  プロジェクトのルートディレクトリにある `local.properties` ファイルを開きます。
3.  以下の行を追加してください（`""` で囲ってください）:
    OPEN_WEATHER_API_KEY="APIキーをこちらに入れ替えてください。"
4.  Android Studio でプロジェクトを同期 (Sync Project with Gradle Files) し、実行します。
*※ セキュリティのため、API キーは Git に含まれていません。`local.properties.example` を参考にしてください。*


## 技術概要
WeatherForecastApp は、Android 向けに Kotlin と Jetpack Compose を用いて開発した 5 日間の天気予報アプリです。
今回のアップデートで、**GPSによる現在地取得機能**を追加し、より利便性を高めました。

### 実装済み機能
- **現在地取得 (GPS)**: Google Play Services の `FusedLocationProviderClient` を使用し、ワンタップで現在地の予報を表示。
- **都市検索/緯度経度入力**: 都市リストからの選択、または手動での座標入力による詳細検索。
- **データ永続化 (Room)**: 取得データをローカルDBに保存し、オフライン環境でも閲覧可能。
- **同日キャッシュポリシー**: JST（日本標準時）を基準に日付を判定。同日内であればキャッシュを表示し、APIリクエストを最小限に抑制。
- **エラーハンドリング**: ネットワーク未接続やGPSオフ時のエラー表示、およびリトライ機能。

## Tech Stack / 技術スタック
- **Language / 言語:** Kotlin
- **UI:** Jetpack Compose, Navigation Compose
- **Architecture / アーキテクチャ:** MVVM + Repository (Clean Architecture を意識した設計)
- **DI:** Hilt
- **Networking / ネットワーク:** Retrofit, OkHttp, Moshi
- **Location:** Google Play Services Location (FusedLocationProviderClient)
- **Persistence / 永続化:** Room (SQLite)
- **Async / 非同期処理:** Coroutines + Flow
- **Images / 画像表示:** Coil (天気アイコン)
- **Unit Test:** MockK, Turbine (ViewModel の状態遷移テスト)

## バージョン管理
バージョン管理は Gradle の defaultConfig により行っています。
defaultConfig {
versionCode = 1
versionName = "1.0.0"
}
* 大規模プロジェクトでは、version.properties ファイルや CI/CD パイプラインを用いて自動的にバージョンを更新することが可能です。

## アーキテクチャと設計のポイント
- **LocationHelper の導入**: 位置情報取得ロジックを UI から分離し、`util` クラスとしてカプセル化。保守性とテスト可能性を向上させました。
- **ランタイム権限ハンドリング**: `rememberLauncherForActivityResult` を使用した最新の Compose 権限リクエスト手法を採用。
- **セキュリティ**: API キーを `local.properties` で管理し、`BuildConfig` 経由で安全に参照。


## 機能
- **ホーム画面**: 都市選択、手動座標入力、および「現在地を取得」ボタン。
- **予報画面**: 3時間ごとの天気（アイコン、気温、時刻）をリスト表示。
- **オフライン対応**: 最後に成功したリクエストの結果をキャッシュから復元。


## 制限事項
- 予報の粒度は 3 時間単位（OpenWeatherMap API の仕様）。
- 位置情報の精度は `PRIORITY_HIGH_ACCURACY` を設定していますが、屋内等では取得に時間がかかる場合があります。
- UI スタイリングは最小限（機能重視）

## 動作デモ (App Demo)
<p align="center">
  <a href="https://youtube.com/shorts/gDSxsIg6Js4">
    <img src="https://img.youtube.com/vi/gDSxsIg6Js4/0.jpg" alt="Watch the demo" width="300" />
    <br>
    <b>Click to watch the Demo on YouTube</b>
  </a>
</p>


## License / ライセンス
Copyright (c) 2024 Patrician Andres.
This project is for technical evaluation purposes.
本プロジェクトは技術評価目的で作成されたものです。

## Developer / 開発者
- **Name:** Patrician Andres
