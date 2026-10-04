# Rodina Mobile Bot — Сборка APK

Это готовый Android-проект для автоматизации входа на сервер по IP/паролю и AFK-фарма.

## Быстрый запуск

### 1. Компиляция и сборка APK

**На Windows (PowerShell или CMD):**
```bash
./gradlew.bat assembleRelease
```

**На macOS/Linux:**
```bash
./gradlew assembleRelease
```

APK будет создан в папке: `app/build/outputs/apk/release/app-release.apk`

### 2. Установка на устройство

**Через ADB:**
```bash
adb install app/build/outputs/apk/release/app-release.apk
```

**Или вручную:**
- Скопируйте `app-release.apk` на Android-устройство
- Откройте файловый менеджер
- Нажмите на APK и установите приложение

## Требования

- Android SDK 26+
- Android Studio (опционально, можно собрать через командную строку)
- Java 17+
- Gradle 8.0+

## Функции в версии 0.1.0

✅ **Профили серверов**
- Сохранение IP, порта, пароля
- Редактирование настроек профиля
- Добавление новых профилей

✅ **Сценарии автоматизации**
- Подключение по IP:порт
- Введение пароля
- Ожидание (в ms)
- AFK loop (циклические действия)

✅ **UI на Jetpack Compose**
- Синяя/чёрная тема
- Список профилей
- Редактор профиля
- Логи выполнения

✅ **Accessibility Service**
- Базовая интеграция
- Готовность к расширению функций

## Структура проекта

```
rodina-mobile-bot/
├── app/
│   ├── src/main/
│   │   ├── java/com/rodina/mobilebot/
│   │   │   ├── MainActivity.kt              # Главный экран
│   │   │   ├── data/
│   │   │   │   ├── AutomationProfile.kt    # Модель профиля
│   │   │   │   └── AutomationUiState.kt    # Состояние UI
│   │   │   ├── engine/
│   │   │   │   └── AutomationEngine.kt     # Движок сценариев
│   │   │   ├── service/
│   │   │   │   ├── RodinaAccessibilityService.kt
│   │   │   │   └── AfkAutomationService.kt
│   │   │   └── ui/theme/
│   │   │       └── Theme.kt                 # Тема
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   └── themes.xml
│   │   │   └── xml/
│   │   │       └── accessibility_service_config.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

## Сборка Debug APK (для тестирования)

```bash
./gradlew assembleDebug
```

Debug APK: `app/build/outputs/apk/debug/app-debug.apk`

## Для разработки в Android Studio

1. Откройте проект в Android Studio
2. Дождитесь синхронизации Gradle
3. Подключите устройство через USB или запустите эмулятор
4. Нажмите **Run** (Shift+F10) для запуска debug-версии

## Следующие версии будут включать

- Сохранение профилей в Room/DataStore
- Расширенный редактор сценариев
- Таймер и запуск по расписанию
- Логирование в файл
- Поддержка импорта/экспорта профилей
- Уведомления о статусе
- Поддержка прокси и сетевых настроек

## Лицензия и использование

Используйте Accessibility Service только для приложений и сценариев, которыми вы владеете или имеете разрешение на использование.

## Контакты и поддержка

Репозиторий: https://github.com/MAKS297485/rodina-mobile-bot
