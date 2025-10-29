package com.example.peacenest.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.peacenest.data.ThemePreferences

private fun getSereneLightScheme() = lightColorScheme(
    primary = SerenePrimaryLight,
    secondary = SereneSecondaryLight,
    tertiary = SereneTertiaryLight,
    background = SereneBackgroundLight,
    surface = SereneSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1A1625),
    onSurface = Color(0xFF1A1625)
)

private fun getSereneDarkScheme() = darkColorScheme(
    primary = SerenePrimaryDark,
    secondary = SereneSecondaryDark,
    tertiary = SereneTertiaryDark,
    background = SereneBackgroundDark,
    surface = SereneSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE4E1EC),
    onSurface = Color(0xFFE4E1EC)
)

private fun getForestLightScheme() = lightColorScheme(
    primary = ForestPrimaryLight,
    secondary = ForestSecondaryLight,
    tertiary = ForestTertiaryLight,
    background = ForestBackgroundLight,
    surface = ForestSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF0D1F0F),
    onSurface = Color(0xFF0D1F0F)
)

private fun getForestDarkScheme() = darkColorScheme(
    primary = ForestPrimaryDark,
    secondary = ForestSecondaryDark,
    tertiary = ForestTertiaryDark,
    background = ForestBackgroundDark,
    surface = ForestSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFDFF0E0),
    onSurface = Color(0xFFDFF0E0)
)

private fun getSunsetLightScheme() = lightColorScheme(
    primary = SunsetPrimaryLight,
    secondary = SunsetSecondaryLight,
    tertiary = SunsetTertiaryLight,
    background = SunsetBackgroundLight,
    surface = SunsetSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF2C1810),
    onSurface = Color(0xFF2C1810)
)

private fun getSunsetDarkScheme() = darkColorScheme(
    primary = SunsetPrimaryDark,
    secondary = SunsetSecondaryDark,
    tertiary = SunsetTertiaryDark,
    background = SunsetBackgroundDark,
    surface = SunsetSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFFFF3E0),
    onSurface = Color(0xFFFFF3E0)
)

private fun getOceanLightScheme() = lightColorScheme(
    primary = OceanPrimaryLight,
    secondary = OceanSecondaryLight,
    tertiary = OceanTertiaryLight,
    background = OceanBackgroundLight,
    surface = OceanSurfaceLight,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF0D1B2A),
    onSurface = Color(0xFF0D1B2A)
)

private fun getOceanDarkScheme() = darkColorScheme(
    primary = OceanPrimaryDark,
    secondary = OceanSecondaryDark,
    tertiary = OceanTertiaryDark,
    background = OceanBackgroundDark,
    surface = OceanSurfaceDark,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFFE1F5FE),
    onSurface = Color(0xFFE1F5FE)
)

@Composable
fun PeacenestTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val currentTheme = ThemePreferences.currentTheme.value
    val isDarkMode = ThemePreferences.isDarkMode.value

    val colorScheme = when (currentTheme) {
        ThemeType.SERENE -> if (isDarkMode) getSereneDarkScheme() else getSereneLightScheme()
        ThemeType.FOREST -> if (isDarkMode) getForestDarkScheme() else getForestLightScheme()
        ThemeType.SUNSET -> if (isDarkMode) getSunsetDarkScheme() else getSunsetLightScheme()
        ThemeType.OCEAN -> if (isDarkMode) getOceanDarkScheme() else getOceanLightScheme()
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkMode
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}