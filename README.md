# Rodina Mobile Bot — Готовая версия 0.1.0

🤖 **Android-бот для автоматизации входа на сервер и AFK-фарма**

## Быстрый старт

### 📦 Скачать готовый APK

1. **Сборка APK:**
   - **Windows:** запустите `build.bat`
   - **macOS/Linux:** запустите `./build.sh` или `./gradlew assembleRelease`

2. **Установка на Android-устройство:**
   ```bash
   adb install app/build/outputs/apk/release/app-release.apk
   ```

3. **Или вручную:**
   - Скопируйте APK на устройство
   - Откройте и нажмите установить

## ✨ Функции MVP v0.1.0

### Основное
- ✅ Управление профилями серверов (IP, порт, пароль)
- ✅ Редактирование профилей прямо в приложении
- ✅ Движок сценариев (CONNECT → PASSWORD → WAIT → AFK_LOOP)
- ✅ Логирование действий в реальном времени
- ✅ Синяя/чёрная тема оформления

### Технология
- Kotlin + Jetpack Compose
- Material3 дизайн
- Accessibility Service API
- WorkManager для фоновых задач
- DataStore для сохранения настроек

## 📋 Требования для сборки

- **Android SDK:** 26+
- **Java:** 17+
- **Gradle:** 8.0+
- **Android Studio:** 2023.1+ (опционально)

## 🚀 Компиляция

### Вариант 1: Через скрипт
```bash
# Linux/macOS
./build.sh

# Windows
build.bat
```

### Вариант 2: Через Gradle напрямую
```bash
# Debug версия (для тестирования)
./gradlew assembleDebug

# Release версия (для продакшена)
./gradlew assembleRelease
```

### Вариант 3: Через Android Studio
1. Откройте проект
2. Build → Build Bundle(s) / APK(s) → Build APK(s)
3. Выберите вариант сборки

## 📱 Установка на устройство

```bash
# Убедитесь, что adb установлен и девайс подключен
adb devices

# Установите APK
adb install app/build/outputs/apk/release/app-release.apk

# Или запустите приложение
adb shell am start -n com.rodina.mobilebot/.MainActivity
```

## 🎮 Использование приложения

### На главном экране:
1. **Start automation** — запустить выбранный профиль
2. **Add profile** — добавить новый сервер
3. **Edit** (на карточке профиля) — редактировать настройки

### При редактировании профиля:
- **Name** — название профиля
- **IP address** — IP сервера
- **Port** — порт подключения
- **Password** — пароль для входа
- **Method** — описание типа сценария
- **Repeat count** — количество повторов
- **Delay ms** — задержка между действиями

### Логи:
Показывают статус выполнения сценария в реальном времени.

## 📂 Структура проекта

```
rodina-mobile-bot/
├── app/
│   ├── src/main/
│   │   ├── java/com/rodina/mobilebot/
│   │   │   ├── MainActivity.kt              # UI + логика
│   │   │   ├── data/                        # Модели данных
│   │   │   ├── engine/                      # Движок сценариев
│   │   │   ├── service/                     # Android сервисы
│   │   │   └── ui/                          # Compose UI
│   │   ├── res/                             # Ресурсы
│   │   └── AndroidManifest.xml              # Конфигурация
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── build.sh                                 # Linux/macOS скрипт
├── build.bat                                # Windows скрипт
├── BUILD_GUIDE.md                           # Подробное руководство
└── README.md
```

## 🔐 Безопасность

⚠️ **Важно:**
- Пароли хранятся в памяти приложения (в будущих версиях будет шифрование)
- Используйте Accessibility Service только для своих приложений
- Не используйте для несанкционированного доступа
- Compliance с политикой Google Play требует явных разрешений

## 🛠️ Возможные проблемы при сборке

### Ошибка: `Command 'gradlew' not found`
**Решение:** Используйте полный путь `./gradlew` (Linux/macOS) или `gradlew.bat` (Windows)

### Ошибка: `ANDROID_SDK_ROOT not set`
**Решение:** Установите переменную окружения:
```bash
export ANDROID_SDK_ROOT=/path/to/android/sdk  # Linux/macOS
set ANDROID_SDK_ROOT=C:\Android\sdk           # Windows
```

### Ошибка: `No matching variant`
**Решение:** Убедитесь что установлены нужные версии SDK (34, 33, 26)

## 🚀 Следующие версии

- [ ] Сохранение профилей в Room Database
- [ ] Импорт/экспорт конфигураций
- [ ] Расширенный редактор сценариев
- [ ] Таймер и автозапуск по расписанию
- [ ] Уведомления о статусе
- [ ] Поддержка прокси
- [ ] Analytics и статистика использования

## 📞 Поддержка

- **GitHub:** https://github.com/MAKS297485/rodina-mobile-bot
- **Issues:** Сообщайте об ошибках через GitHub Issues

## 📄 Лицензия

Этот проект предоставляется в образовательных целях. Используйте ответственно.

---

**Версия:** 0.1.0  
**Статус:** MVP (Минимальный жизнеспособный продукт)  
**Обновлено:** 2026-10-04
