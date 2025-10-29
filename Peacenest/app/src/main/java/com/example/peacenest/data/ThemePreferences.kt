package com.example.peacenest.data

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.peacenest.ui.theme.ThemeType

object ThemePreferences {
    private const val PREFS_NAME = "theme_prefs"
    private const val KEY_THEME = "selected_theme"
    private const val KEY_DARK_MODE = "dark_mode"

    val currentTheme = mutableStateOf(ThemeType.SERENE)
    val isDarkMode = mutableStateOf(false)

    fun saveTheme(context: Context, theme: ThemeType) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_THEME, theme.name).apply()
        currentTheme.value = theme
    }

    fun saveDarkMode(context: Context, isDark: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(KEY_DARK_MODE, isDark).apply()
        isDarkMode.value = isDark
    }

    fun loadTheme(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val themeName = prefs.getString(KEY_THEME, ThemeType.SERENE.name) ?: ThemeType.SERENE.name
        val darkMode = prefs.getBoolean(KEY_DARK_MODE, false)
        
        currentTheme.value = try {
            ThemeType.valueOf(themeName)
        } catch (e: IllegalArgumentException) {
            ThemeType.SERENE
        }
        
        isDarkMode.value = darkMode
    }
}

