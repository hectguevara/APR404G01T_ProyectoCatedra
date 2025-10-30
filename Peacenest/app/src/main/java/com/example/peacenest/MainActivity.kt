package com.example.peacenest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.peacenest.data.ThemePreferences
import com.example.peacenest.navigation.MainApp
import com.example.peacenest.ui.theme.PeacenestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cargar preferencias de tema
        ThemePreferences.loadTheme(this)

        setContent {
            PeacenestTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    // Muestra directamente la app con la barra de navegación
    MainApp()
}