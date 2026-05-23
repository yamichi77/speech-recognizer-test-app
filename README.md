# speech-recognizer-test-app

Android の `SpeechRecognizer` API を使った音声認識テスト用アプリです。

## 概要

このアプリは、端末の音声認識サービスを呼び出して、認識途中のテキストと認識結果を画面に表示します。

- `Start` ボタンで音声認識を開始
- `Stop` ボタンで音声認識を停止
- 部分認識結果と最終認識結果を `TextView` に表示

## 必要環境

- Android Studio
- Android Gradle Plugin 7.3.1
- Android SDK 33
- Android 13 以上の端末またはエミュレーター
- 音声認識サービスが利用できる環境

## 権限

音声認識のため、以下の権限を使用します。

- `RECORD_AUDIO`
- `INTERNET`

初回起動時にマイク権限を許可してください。

## ビルド

```sh
./gradlew assembleDebug
```

Windows では以下を使用できます。

```bat
gradlew.bat assembleDebug
```

## 実行

Android Studio でプロジェクトを開き、端末またはエミュレーターを選択して実行します。

アプリ起動後、`Start` を押して話すと認識結果が表示されます。認識を終了する場合は `Stop` を押します。
