# WeatherForecastApp

## 技術概要 (Tech Overview in Japanese)
WeatherForecastApp は、Android 向けに Kotlin と Jetpack Compose を用いて開発した 5 日間の天気予報アプリです。  
ユーザーは以下の機能を利用できます:
- 都市選択（東京、兵庫、大分、北海道）または現在地の利用
- OpenWeatherMap API を用いた現在の天気と 5 日間の予報表示
- ネットワークエラー時のリトライ機能
- 同日キャッシュポリシーによるオフライン表示

このプロジェクトは、Android アーキテクチャ、API 連携、キャッシュ機能、Git ワークフローを示すための技術課題として作成しました。

---

## Tech Stack / 技術スタック
- **Language / 言語:** Kotlin
- **UI:** Jetpack Compose, Navigation Compose
- **Architecture / アーキテクチャ:** MVVM + Repository
- **DI:** Hilt
- **Networking / ネットワーク:** Retrofit, OkHttp, Moshi
- **Persistence / 永続化:** Room (SQLite)
- **Async / 非同期処理:** Coroutines + Flow
- **Images / 画像表示:** Coil (天気アイコン)

---
バージョン管理
バージョン管理は Gradle の defaultConfig により行っています。
defaultConfig {
versionCode = 1
versionName = "1.0.0"
}

大規模プロジェクトでは、version.properties ファイルや CI/CD パイプラインを用いて自動的にバージョンを更新することが可能です。

アーキテクチャ
UI 層は ViewModel の状態を監視し、ユーザー操作（予報取得、リトライ）を処理します。 Repository は Retrofit（ネットワーク）と Room（キャッシュ）を統合し、同日 JST キャッシュポリシーを適用します。

機能
都市リスト: 東京、兵庫、大分、北海道、現在地
予報画面: 天気アイコン、気温（℃）、JST 時刻
エラーハンドリング: 「通信に失敗しました。」メッセージとリトライボタン
オフライン対応: ネットワークが利用できない場合でもキャッシュ予報を表示

制限事項
予報の粒度は 3 時間単位（OpenWeatherMap API の仕様）
キャッシュは 1 日単位で失効、長期保存はなし
UI スタイリングは最小限（機能重視）
位置情報はランタイムパーミッションが必要。拒否された場合は都市選択が必須

スクリーンショット

License / ライセンス
This project is for technical evaluation purposes. 本プロジェクトは技術評価目的で作成されたものです。


