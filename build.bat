@echo off
REM Build script for Rodina Mobile Bot (Windows)

echo Rodina Mobile Bot - Build Script
echo =================================
echo.

echo Step 1: Cleaning previous builds...
call gradlew.bat clean

echo.
echo Step 2: Building Release APK...
call gradlew.bat assembleRelease

echo.
echo Step 3: Build complete!
echo.
echo APK location: app\build\outputs\apk\release\app-release.apk
echo.
echo To install on your device:
echo   adb install app\build\outputs\apk\release\app-release.apk
echo.
pause
