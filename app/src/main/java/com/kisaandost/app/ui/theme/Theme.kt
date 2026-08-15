package com.kisaandost.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryEmerald,
    secondary = SecondaryGreenPill,
    tertiary = AccentYellow,
    background = ForestDarkBackground,
    surface = ForestDarkSurface,
    surfaceVariant = ForestDarkCard,
    outline = ForestCardBorder,
    onPrimary = DarkTextPrimary,
    onSecondary = DarkTextPrimary,
    onBackground = DarkTextPrimary,
    onSurface = DarkTextPrimary,
    onSurfaceVariant = DarkTextSecondary
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimaryGreen,
    secondary = SecondaryGreenPill,
    tertiary = AccentYellow,
    background = LightBackground,
    surface = LightSurface,
    surfaceVariant = LightSurface,
    outline = LightCardBorder,
    onPrimary = LightSurface,
    onSecondary = LightSurface,
    onBackground = LightTextPrimary,
    onSurface = LightTextPrimary,
    onSurfaceVariant = LightTextSecondary
)

@Composable
fun KisaanDostTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
