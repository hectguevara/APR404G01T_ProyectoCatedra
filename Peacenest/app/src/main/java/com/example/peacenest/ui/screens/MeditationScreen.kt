package com.example.peacenest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun MeditationScreen(navController: NavController) {
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
                        "Meditaciones Guiadas",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "Encuentra paz interior y claridad mental",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    )
                }
            }

            // Contenido principal
            MeditationSessionsList(navController = navController)
        }
    }
}

@Composable
fun MeditationSessionsList(navController: NavController) {
    val meditationSessions = listOf(
        MeditationSession(
            id = "1",
            title = "Meditación para Relajación Profunda",
            description = "Sesión guiada para liberar tensión y encontrar calma",
            emoji = "😌",
            duration = "15 min",
            level = "Principiante",
            type = "youtube",
            benefits = listOf("Reduce estrés", "Mejora calidad de sueño", "Calma la mente")
        ),
        MeditationSession(
            id = "2",
            title = "Mindfulness para Ansiedad",
            description = "Técnicas de atención plena para manejar la ansiedad",
            emoji = "🌀",
            duration = "10 min",
            level = "Intermedio",
            type = "mindfulness",
            benefits = listOf("Reduce ansiedad", "Aumenta conciencia", "Mejora enfoque")
        ),
        MeditationSession(
            id = "3",
            title = "Meditación con Sonidos de Naturaleza",
            description = "Inmersión en sonidos naturales para meditación profunda",
            emoji = "🌿",
            duration = "20 min",
            level = "Principiante",
            type = "pixabay",
            benefits = listOf("Conecta con naturaleza", "Relajación profunda", "Armonía interior")
        ),
        MeditationSession(
            id = "4",
            title = "Meditación para Concentración",
            description = "Mejora tu enfoque y claridad mental",
            emoji = "🎯",
            duration = "12 min",
            level = "Intermedio",
            type = "youtube",
            benefits = listOf("Mejora concentración", "Claridad mental", "Productividad")
        ),
        MeditationSession(
            id = "5",
            title = "Meditación Amorosa Bondad",
            description = "Cultiva compasión hacia ti mismo y los demás",
            emoji = "💖",
            duration = "18 min",
            level = "Avanzado",
            type = "mindfulness",
            benefits = listOf("Desarrolla compasión", "Mejora relaciones", "Bienestar emocional")
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(meditationSessions) { session ->
            MeditationSessionCard(
                session = session,
                onCardClick = {
                    // Navegar a reproductor según el tipo
                    when (session.type) {
                        "youtube" -> {
                            // navController.navigate("${Routes.MeditationPlayer.route}/${session.id}")
                        }
                        "pixabay" -> {
                            // navController.navigate("${Routes.AudioPlayer.route}/${session.id}")
                        }
                        "mindfulness" -> {
                            // navController.navigate("${Routes.Mindfulness.route}/${session.id}")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun MeditationSessionCard(
    session: MeditationSession,
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
                            session.emoji,
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
                        session.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        session.description,
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
                MeditationInfoChip(
                    icon = "⏱️",
                    text = session.duration
                )

                // Nivel
                MeditationInfoChip(
                    icon = "📊",
                    text = session.level
                )

                // Tipo
                MeditationInfoChip(
                    icon = when (session.type) {
                        "youtube" -> "🎥"
                        "pixabay" -> "🎵"
                        "mindfulness" -> "🧠"
                        else -> "🎯"
                    },
                    text = when (session.type) {
                        "youtube" -> "Video"
                        "pixabay" -> "Audio"
                        "mindfulness" -> "Guiada"
                        else -> "Meditación"
                    }
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
                session.benefits.forEach { benefit ->
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
                    "Comenzar Meditación",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun MeditationInfoChip(icon: String, text: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
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

// Data class para las sesiones de meditación
data class MeditationSession(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val duration: String,
    val level: String,
    val type: String,
    val benefits: List<String>
)