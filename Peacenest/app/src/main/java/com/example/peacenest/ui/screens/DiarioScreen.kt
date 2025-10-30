package com.example.peacenest.ui.screens

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
fun DiarioScreen(navController: NavController) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var selectedEmotion by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

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
                .padding(20.dp)
        ) {
            // Header
            Text(
                "Tu Diario de Emociones",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Registra cómo te sientes hoy",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            // Selector de emociones
            EmotionSelectorCard(
                selectedEmotion = selectedEmotion,
                onEmotionSelected = { emotion -> selectedEmotion = emotion }
            )

            Spacer(Modifier.height(24.dp))

            // Campo de descripción
            DescriptionCard(
                description = description,
                onDescriptionChange = { description = it }
            )

            Spacer(Modifier.height(24.dp))

            // Botón guardar
            SaveButton(
                enabled = selectedEmotion.isNotEmpty(),
                onClick = {
                    // Aquí guardarías la entrada en la base de datos
                    // Por ahora solo mostramos un mensaje
                    description = ""
                    selectedEmotion = ""
                }
            )

            Spacer(Modifier.height(32.dp))

            // Historial de emociones
            MoodHistorySection()
        }
    }
}

@Composable
fun EmotionSelectorCard(
    selectedEmotion: String,
    onEmotionSelected: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Icono y título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    "😊",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "¿Cómo te sientes hoy?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // Selector de emociones
            val emotions = listOf(
                "😊 Feliz" to "feliz",
                "😢 Triste" to "triste",
                "😠 Enojado" to "enojado",
                "😌 Relajado" to "relajado",
                "😐 Neutral" to "neutral"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                emotions.forEach { (emoji, emotion) ->
                    EmotionChip(
                        emoji = emoji,
                        emotion = emotion,
                        isSelected = selectedEmotion == emotion,
                        onClick = { onEmotionSelected(emotion) }
                    )
                }
            }
        }
    }
}

@Composable
fun EmotionChip(
    emoji: String,
    emotion: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.size(60.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 2.dp,
            pressedElevation = 12.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                emoji,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun DescriptionCard(
    description: String,
    onDescriptionChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    "📝",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "Describe tu día",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Campo de texto
            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                placeholder = {
                    Text(
                        "Escribe sobre tus pensamientos, emociones o cómo fue tu día...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        }
    }
}

@Composable
fun SaveButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        enabled = enabled,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (enabled) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Text(
            if (enabled) "💾 Guardar Emoción" else "Selecciona una emoción primero",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun MoodHistorySection() {
    // Por ahora mostramos un placeholder
    // En una implementación real, aquí cargarías el historial de la base de datos

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Título
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    "📊",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    "Tu Historial",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Placeholder del historial
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Aquí verás tu historial de emociones\n(Próximamente)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}