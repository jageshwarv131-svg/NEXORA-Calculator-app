package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = CoralRedLight,
    onPrimary = Color.White,
    primaryContainer = CoralRedDark,
    onPrimaryContainer = Color.White,
    secondary = CyanAccentLight,
    onSecondary = Slate900,
    background = Color(0xFF0F172A),
    onBackground = Color.White,
    surface = Color(0xCC0F172A),
    onSurface = Color.White,
    surfaceVariant = Color(0x33FFFFFF),
    onSurfaceVariant = Slate200
)

private val LightColorScheme = lightColorScheme(
    primary = CoralRed,
    onPrimary = Color.White,
    primaryContainer = CoralRedLight,
    onPrimaryContainer = Color.White,
    secondary = CyanAccent,
    onSecondary = Color.White,
    background = Color(0xFFF8FAFC),
    onBackground = Slate900,
    surface = Color(0xCCFFFFFF),
    onSurface = Slate900,
    surfaceVariant = Color(0x99FFFFFF),
    onSurfaceVariant = Slate700
)

@Composable
fun NexoraCalculatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
