// navigation/MainApp.kt
package com.example.peacenest.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.padding
import com.example.peacenest.ui.screens.HomeScreen
import com.example.peacenest.ui.screens.DiarioScreen
import com.example.peacenest.ui.screens.BreathingScreen
import com.example.peacenest.ui.screens.BreathingExerciseScreen
import com.example.peacenest.ui.screens.MeditationExerciseScreen
import com.example.peacenest.ui.screens.DailyTipsScreen
import com.example.peacenest.ui.screens.MenuScreen
import com.example.peacenest.ui.screens.PerfilScreen
import com.example.peacenest.ui.screens.MeditationScreen
import com.example.peacenest.ui.screens.EducationalScreen
import com.example.peacenest.ui.screens.FavoritesScreen
import com.example.peacenest.ui.screens.ShopScreen
import com.example.peacenest.ui.settings.SettingsScreen

@Composable
fun MainApp(onLogout: () -> Unit = {}) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.Home.route) {
                HomeScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.Diario.route) {
                DiarioScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.Bienestar.route) {
                BreathingScreen(navController = navController, onLogout = onLogout)
            }
            composable("${Routes.BreathingExercise.route}/{techniqueId}") { backStackEntry ->
                val techniqueId = backStackEntry.arguments?.getString("techniqueId") ?: "1"
                BreathingExerciseScreen(navController = navController, techniqueId = techniqueId)
            }
            composable(Routes.Menu.route) {
                MenuScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.Perfil.route) {
                PerfilScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.Meditation.route) {
                MeditationScreen(navController = navController, onLogout = onLogout)
            }
            composable("${Routes.MeditationExercise.route}/{meditationId}") { backStackEntry ->
                val meditationId = backStackEntry.arguments?.getString("meditationId") ?: "1"
                MeditationExerciseScreen(navController = navController, meditationId = meditationId)
            }
            composable(Routes.Articles.route) {
                EducationalScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.Shop.route) {
                ShopScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.DailyTips.route) {
                DailyTipsScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.Favorites.route) {
                FavoritesScreen(navController = navController, onLogout = onLogout)
            }
            composable(Routes.Settings.route) {
                SettingsScreen(navController = navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        tonalElevation = 8.dp
    ) {
        Routes.bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title
                    )
                },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}