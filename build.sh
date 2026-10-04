#!/bin/bash
# Build script for Rodina Mobile Bot

echo "Rodina Mobile Bot - Build Script"
echo "================================="
echo ""

echo "Step 1: Cleaning previous builds..."
./gradlew clean

echo ""
echo "Step 2: Building Release APK..."
./gradlew assembleRelease

echo ""
echo "Step 3: Build complete!"
echo ""
echo "APK location: app/build/outputs/apk/release/app-release.apk"
echo ""
echo "To install on your device:"
echo "  adb install app/build/outputs/apk/release/app-release.apk"
echo ""
