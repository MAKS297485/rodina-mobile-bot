# Rodina Mobile Bot

Первая версия проекта для Android-бота с автоматизацией входа на сервер по IP и паролю и сценариями AFK-фарма.

Описание проекта
- Android app на Kotlin + Jetpack Compose
- Тёмная тема: синий/чёрный стиль
- Хранение профилей серверов
- Старт автоматизации
- Базовый Accessibility Service для управления приложением
- Поддержка сценариев: вход по IP/пароле, ожидание, клики, циклическая авто-активация

Структура проекта
- app/src/main/java/com/rodina/mobilebot/MainActivity.kt - главный экран
- app/src/main/java/com/rodina/mobilebot/data/AutomationProfile.kt - модель профиля
- app/src/main/java/com/rodina/mobilebot/service/RodinaAccessibilityService.kt - сервис доступности
- app/src/main/java/com/rodina/mobilebot/service/AfkAutomationService.kt - сервис автоматизации
- app/src/main/res/xml/accessibility_service_config.xml - конфигурация Accessibility Service

План MVP
1. Список сохранённых серверов
2. Поля: название, IP, порт, пароль
3. Кнопка запуска сценария
4. Базовый AFK-цикл: задержка и повторяемые действия
5. Доступность/авто-перемещение по интерфейсу
6. Чёрно-синяя тема UI

Как запустить
1. Откройте проект в Android Studio.
2. Убедитесь, что установлен Android SDK.
3. Подключите устройство или эмулятор Android.
4. Нажмите Run.

Важно
- Используйте Accessibility Service только для приложений, которыми вы владеете или используете по правилам платформы.
- Убедитесь, что вы соблюдаете политики Google Play и требования к доступности.

Следующий шаг
- Добавить хранение профилей в Room
- Реализовать сценарии шагов: Tap, Wait, Swipe, Input, Reconnect
- Добавить таймеры и логирование
- Подготовить настройки и панель статуса
