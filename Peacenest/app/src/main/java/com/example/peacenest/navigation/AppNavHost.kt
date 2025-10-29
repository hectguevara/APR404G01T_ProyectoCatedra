package com.example.peacenest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.peacenest.ui.auth.LoginScreen
import com.example.peacenest.ui.auth.RegisterScreen
import com.example.peacenest.ui.home.HomeScreen
import com.example.peacenest.ui.breathing.BreathingScreen
import com.example.peacenest.ui.meditation.MeditationScreen
import com.example.peacenest.ui.audios.AudiosScreen
import com.example.peacenest.ui.articles.ArticlesScreen
import com.example.peacenest.ui.tracking.TrackingScreen
import com.example.peacenest.ui.settings.SettingsScreen

sealed class Routes(val route: String) {
    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object Breathing : Routes("breathing")
    object Meditation : Routes("meditation")
    object Audios : Routes("audios")
    object Articles : Routes("articles")
    object Tracking : Routes("tracking")
    object Settings : Routes("settings")
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route) { LoginScreen(navController) }
        composable(Routes.Register.route) { RegisterScreen(navController) }
        composable(Routes.Home.route) { HomeScreen(navController) }
        composable(Routes.Breathing.route) { BreathingScreen(navController) }
        composable(Routes.Meditation.route) { MeditationScreen(navController) }
        composable(Routes.Audios.route) { AudiosScreen(navController) }
        composable(Routes.Articles.route) { ArticlesScreen(navController) }
        composable(Routes.Tracking.route) { TrackingScreen(navController) }
        composable(Routes.Settings.route) { SettingsScreen(navController) }
    }
}
