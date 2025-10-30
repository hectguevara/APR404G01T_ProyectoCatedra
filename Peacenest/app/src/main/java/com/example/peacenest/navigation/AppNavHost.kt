package com.example.peacenest.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(val route: String, val title: String, val icon: ImageVector) {
    object Login : Routes("login", "Login", Icons.Default.Home)
    object Register : Routes("register", "Registro", Icons.Default.Home)
    object Home : Routes("home", "Inicio", Icons.Default.Home)
    object Diario : Routes("diario", "Diario", Icons.Default.Edit)
    object Bienestar : Routes("breathing", "Bienestar", Icons.Default.Favorite)
    object Menu : Routes("menu", "Menú", Icons.Default.Menu)
    object Perfil : Routes("perfil", "Perfil", Icons.Default.Person)
    object Breathing : Routes("breathing", "Respiración", Icons.Default.Home)
    object Meditation : Routes("meditation", "Meditación", Icons.Default.Home)
    object Articles : Routes("articles", "Artículos", Icons.Default.Home)
    object Tracking : Routes("tracking", "Seguimiento", Icons.Default.Home)
    object Settings : Routes("settings", "Ajustes", Icons.Default.Home)
    object Shop : Routes("shop", "Tienda", Icons.Default.Home)
    object DailyTips : Routes("dailytips", "Consejos Diarios", Icons.Default.Home)
    object Favorites : Routes("favorites", "Favoritos", Icons.Default.Home)
    // Items que estarán en la barra de navegación
    companion object {
        val bottomNavItems = listOf(
            Home,
            Diario,
            Bienestar,
            Menu,
            Perfil
        )
    }
}