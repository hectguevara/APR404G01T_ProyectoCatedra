package com.example.peacenest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peacenest.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController, onLogout: () -> Unit = {}) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Diálogo de confirmación de cerrar sesión
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = { Text("Cerrar Sesión", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que deseas cerrar sesión?") },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) { Text("Cerrar Sesión") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("PeaceNest ", fontWeight = FontWeight.Bold)
                        Text("🌿")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.Settings.route) }) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Configuración",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { showLogoutDialog = true }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar Sesión",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Header con bloque de color
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 30.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Menú Principal",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "Todas tus herramientas organizadas",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                }
            }

            // Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                MenuCategories(navController = navController)
            }
        }
    }
}

@Composable
fun MenuCategories(navController: NavController) {
    val menuCategories = listOf(
        MenuCategory(
            title = "Bienestar",
            emoji = "🧘‍♀️",
            items = listOf(
                MenuItem(
                    name = "Meditaciones Guiadas",
                    description = "Sesiones para calmar la mente",
                    route = Routes.Meditation.route,
                    emoji = "🧘"
                ),
                MenuItem(
                    name = "Técnicas de Respiración",
                    description = "Ejercicios anti-ansiedad",
                    route = Routes.Breathing.route,
                    emoji = "🌬️"
                ),
            )
        ),
        MenuCategory(
            title = "Aprendizaje",
            emoji = "📚",
            items = listOf(
                MenuItem(
                    name = "Artículos Educativos",
                    description = "Consejos sobre bienestar y salud mental",
                    route = Routes.Articles.route,
                    emoji = "📖"
                ),
                MenuItem(
                    name = "Consejos Diarios",
                    description = "Frases motivacionales e inspiración",
                    route = Routes.DailyTips.route,
                    emoji = "💡"
                )
            )
        ),
        MenuCategory(
            title = "Personal",
            emoji = "⭐",
            items = listOf(

                MenuItem(
                    name = "Tienda de Frases",
                    description = "Desbloquea frases motivacionales",
                    route = Routes.Shop.route,
                    emoji = "🏪"
                ),
                MenuItem(
                    name = "Consejos Favoritos",
                    description = "Tus frases guardadas",
                    route = Routes.Favorites.route,
                    emoji = "❤️"
                )
            )
        ),
        MenuCategory(
            title = "Configuración",
            emoji = "⚙️",
            items = listOf(
                MenuItem(
                    name = "Ajustes",
                    description = "Personaliza tu experiencia",
                    route = Routes.Settings.route,
                    emoji = "🔧"
                ),
                MenuItem(
                    name = "Acerca de",
                    description = "Información de la app",
                    route = Routes.Settings.route,
                    emoji = "ℹ️"
                )
            )
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        menuCategories.forEach { category ->
            MenuCategoryCard(category = category, navController = navController)
        }
    }
}

@Composable
fun MenuCategoryCard(category: MenuCategory, navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header de categoría
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Card(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(category.emoji, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    category.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Items
            Column {
                category.items.forEachIndexed { index, item ->
                    MenuItemRow(
                        item = item,
                        isLast = index == category.items.size - 1,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun MenuItemRow(item: MenuItem, isLast: Boolean, navController: NavController) {
    Card(
        onClick = { navController.navigate(item.route) },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Card(
                    modifier = Modifier.size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(item.emoji, style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        item.name,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            Text(
                "➡️",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }

    if (!isLast) Spacer(Modifier.height(8.dp))
}

// Data classes para la estructura del menú
data class MenuCategory(
    val title: String,
    val emoji: String,
    val items: List<MenuItem>
)

data class MenuItem(
    val name: String,
    val description: String,
    val route: String,
    val emoji: String
)