package com.example.peacenest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
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
fun BreathingScreen(navController: NavController) {
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
            title = {
                Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
            },
            text = {
                Text("¿Estás seguro de que deseas cerrar sesión?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutDialog = false
                        navController.navigate(Routes.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Cerrar Sesión")
                }
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
                        "Técnicas de Respiración",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "Ejercicios para calmar tu mente y cuerpo",
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
                // Lista de técnicas de respiración
                BreathingTechniquesList(navController = navController)
            }
        }
    }
}

@Composable
fun BreathingTechniquesList(navController: NavController) {
    val breathingTechniques = listOf(
        BreathingTechnique(
            id = "1",
            name = "Respiración 4-7-8",
            shortDescription = "Técnica para conciliar el sueño y reducir ansiedad",
            emoji = "😴",
            duration = "5 min",
            level = "Principiante",
            benefits = listOf("Reduce ansiedad", "Mejora el sueño", "Calma la mente")
        ),
        BreathingTechnique(
            id = "2",
            name = "Respiración Cuadrada",
            shortDescription = "Ejercicio de equilibrio y concentración",
            emoji = "⬜",
            duration = "3 min",
            level = "Intermedio",
            benefits = listOf("Mejora concentración", "Equilibra energía", "Reduce estrés")
        ),
        BreathingTechnique(
            id = "3",
            name = "Respiración Diafragmática",
            shortDescription = "Técnica profunda para relajación completa",
            emoji = "🌊",
            duration = "7 min",
            level = "Principiante",
            benefits = listOf("Relajación profunda", "Mejora oxigenación", "Calma sistema nervioso")
        ),
        BreathingTechnique(
            id = "4",
            name = "Respiración Alternada",
            shortDescription = "Balance energético entre hemisferios cerebrales",
            emoji = "🌀",
            duration = "4 min",
            level = "Avanzado",
            benefits = listOf("Balance energético", "Claridad mental", "Armonía interior")
        ),
        BreathingTechnique(
            id = "5",
            name = "Respiración de Fuego",
            shortDescription = "Ejercicio energizante y purificador",
            emoji = "🔥",
            duration = "2 min",
            level = "Avanzado",
            benefits = listOf("Energiza", "Purifica", "Fortalece pulmones")
        )
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(breathingTechniques) { technique ->
            BreathingTechniqueCard(
                technique = technique,
                onCardClick = {
                    // Navegar a detalle de la técnica
                    // navController.navigate("${Routes.BreathingDetail.route}/${technique.id}")
                }
            )
        }
    }
}

@Composable
fun BreathingTechniqueCard(
    technique: BreathingTechnique,
    onCardClick: () -> Unit
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header con emoji y título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Emoji
                Card(
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            technique.emoji,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                Spacer(Modifier.width(16.dp))

                // Título y descripción
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        technique.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        technique.shortDescription,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Información adicional
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Duración
                InfoChip(
                    icon = "⏱️",
                    text = technique.duration
                )

                // Nivel
                InfoChip(
                    icon = "📊",
                    text = technique.level
                )
            }

            Spacer(Modifier.height(12.dp))

            // Beneficios
            Text(
                "Beneficios:",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column {
                technique.benefits.forEach { benefit ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Text(
                            "• ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            benefit,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            // Botón de comenzar
            Button(
                onClick = onCardClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    "Comenzar Ejercicio",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun InfoChip(icon: String, text: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                icon,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// Data class para las técnicas de respiración
data class BreathingTechnique(
    val id: String,
    val name: String,
    val shortDescription: String,
    val emoji: String,
    val duration: String,
    val level: String,
    val benefits: List<String>
)