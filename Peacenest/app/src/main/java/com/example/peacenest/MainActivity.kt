package com.example.peacenest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peacenest.data.ThemePreferences
import com.example.peacenest.data.TokenManager
import com.example.peacenest.navigation.MainApp
import com.example.peacenest.navigation.Routes
import com.example.peacenest.ui.auth.LoginScreen
import com.example.peacenest.ui.auth.RegisterScreen
import com.example.peacenest.ui.theme.PeacenestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Cargar preferencias de tema
        ThemePreferences.loadTheme(this)
        // Inicializar TokenManager
        TokenManager.init(this)

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
    val navController = rememberNavController()
    
    // NavHost principal que incluye Login, Register y MainApp
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        // Pantallas de autenticación
        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }
        
        composable(Routes.Register.route) {
            RegisterScreen(navController = navController)
        }
        
        // App principal (con barra de navegación)
        composable("main_app") {
            MainApp(
                onLogout = {
                    // Limpiar datos de autenticación
                    TokenManager.clearAuthData()
                    // Navegar al login y limpiar todo el stack
                    navController.navigate(Routes.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}