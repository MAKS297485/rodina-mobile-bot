package com.rodina.mobilebot.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RodinaDarkColorScheme = darkColorScheme(
    primary = Color(0xFF0F4CFF),
    onPrimary = Color.White,
    background = Color(0xFF050A12),
    onBackground = Color(0xFFEAF4FF),
    surface = Color(0xFF0C1627),
    onSurface = Color(0xFFEAF4FF),
    secondary = Color(0xFF5AA9FF),
    tertiary = Color(0xFF173A75)
)

@Composable
fun RodinaMobileBotTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = RodinaDarkColorScheme,
        content = content
    )
}
