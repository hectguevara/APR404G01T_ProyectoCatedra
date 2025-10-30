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
import com.example.peacenest.data.TokenManager
import com.example.peacenest.network.RetrofitClient
import com.example.peacenest.network.models.TrackingRequest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiarioScreen(navController: NavController, onLogout: () -> Unit = {}) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var showLogoutDialog by remember { mutableStateOf(false) }
    var selectedEmotion by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isLoadingHistory by remember { mutableStateOf(false) }
    var historyEntries by remember { mutableStateOf<List<com.example.peacenest.network.models.TrackingEntry>>(emptyList()) }
    var refreshTrigger by remember { mutableStateOf(0) }
    
    // Inicializar TokenManager
    LaunchedEffect(Unit) {
        TokenManager.init(context)
    }
    
    // Cargar historial de emociones
    LaunchedEffect(refreshTrigger) {
        isLoadingHistory = true
        kotlinx.coroutines.delay(500) // Pequeña pausa para asegurar que el token esté disponible
        
        try {
            val token = TokenManager.getAuthHeader()
            android.util.Log.d("DiarioScreen", "=== CARGANDO HISTORIAL ===")
            android.util.Log.d("DiarioScreen", "Token disponible: ${token != null}")
            
            if (token != null) {
                android.util.Log.d("DiarioScreen", "Llamando a getUserTracking...")
                
                val response = RetrofitClient.apiService.getUserTracking(
                    token = token,
                    limit = 10
                )
                
                android.util.Log.d("DiarioScreen", "Response code: ${response.code()}")
                android.util.Log.d("DiarioScreen", "Response successful: ${response.isSuccessful}")
                
                if (response.isSuccessful) {
                    val trackingList = response.body()
                    android.util.Log.d("DiarioScreen", "Body recibido: $trackingList")
                    
                    if (trackingList != null) {
                        android.util.Log.d("DiarioScreen", "Count: ${trackingList.count}")
                        android.util.Log.d("DiarioScreen", "Tracking list size: ${trackingList.tracking.size}")
                        
                        historyEntries = trackingList.tracking
                        
                        android.util.Log.d("DiarioScreen", "✅ Loaded ${historyEntries.size} entries")
                        historyEntries.forEachIndexed { index, entry ->
                            android.util.Log.d("DiarioScreen", "  [$index] mood=${entry.mood}, notes=${entry.notes}, date=${entry.date}")
                        }
                    } else {
                        android.util.Log.e("DiarioScreen", "❌ Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    android.util.Log.e("DiarioScreen", "❌ Error response: $errorBody")
                }
            } else {
                android.util.Log.e("DiarioScreen", "❌ Token is null - user not logged in?")
            }
        } catch (e: Exception) {
            android.util.Log.e("DiarioScreen", "❌ Exception loading history", e)
            android.util.Log.e("DiarioScreen", "Exception type: ${e.javaClass.simpleName}")
            android.util.Log.e("DiarioScreen", "Exception message: ${e.message}")
            e.printStackTrace()
        } finally {
            isLoadingHistory = false
            android.util.Log.d("DiarioScreen", "=== FIN CARGA HISTORIAL ===")
        }
    }
    
    // Función para guardar la emoción en el backend
    fun saveEmotion() {
        if (selectedEmotion.isEmpty()) return
        
        isLoading = true
        scope.launch {
            try {
                val token = TokenManager.getAuthHeader()
                if (token == null) {
                    snackbarHostState.showSnackbar(
                        "Error: No hay sesión activa",
                        duration = SnackbarDuration.Short
                    )
                    return@launch
                }
                
                // Mapear las emociones a los valores del backend
                val moodMap = mapOf(
                    "feliz" to "bien",
                    "triste" to "mal",
                    "enojado" to "muy_mal",
                    "relajado" to "muy_bien",
                    "neutral" to "neutral"
                )
                
                val moodValue = moodMap[selectedEmotion] ?: "neutral"
                
                android.util.Log.d("DiarioScreen", "Selected emotion: $selectedEmotion")
                android.util.Log.d("DiarioScreen", "Mapped mood value: $moodValue")
                
                val trackingRequest = TrackingRequest(
                    mood = moodValue,
                    notes = description.takeIf { it.isNotBlank() },
                    activities = null,
                    date = null // El backend generará la fecha
                )
                
                android.util.Log.d("DiarioScreen", "Sending request: $trackingRequest")
                
                val response = RetrofitClient.apiService.createTracking(token, trackingRequest)
                
                android.util.Log.d("DiarioScreen", "Save response code: ${response.code()}")
                
                if (response.isSuccessful) {
                    snackbarHostState.showSnackbar(
                        "✅ Emoción guardada exitosamente",
                        duration = SnackbarDuration.Short
                    )
                    // Limpiar campos
                    description = ""
                    selectedEmotion = ""
                    // Recargar historial
                    refreshTrigger++
                } else {
                    snackbarHostState.showSnackbar(
                        "Error al guardar: ${response.code()}",
                        duration = SnackbarDuration.Long
                    )
                }
            } catch (e: Exception) {
                snackbarHostState.showSnackbar(
                    "Error de conexión: ${e.message}",
                    duration = SnackbarDuration.Long
                )
            } finally {
                isLoading = false
            }
        }
    }

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
                        onLogout()
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                enabled = selectedEmotion.isNotEmpty() && !isLoading,
                onClick = { saveEmotion() },
                isLoading = isLoading
            )

            Spacer(Modifier.height(32.dp))

            // Historial de emociones
            MoodHistorySection(
                entries = historyEntries,
                isLoading = isLoadingHistory
            )
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
    onClick: () -> Unit,
    isLoading: Boolean = false
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
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                if (enabled) "💾 Guardar Emoción" else "Selecciona una emoción primero",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun MoodHistorySection(
    entries: List<com.example.peacenest.network.models.TrackingEntry>,
    isLoading: Boolean
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

            // Contenido
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (entries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Aún no has guardado ninguna emoción\n¡Empieza ahora!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                // Mostrar entradas
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    entries.take(5).forEach { entry ->
                        MoodHistoryItem(entry)
                    }
                    
                    if (entries.size > 5) {
                        Text(
                            "y ${entries.size - 5} más...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MoodHistoryItem(entry: com.example.peacenest.network.models.TrackingEntry) {
    // Mapear mood a emoji y nombre
    val moodEmoji = when (entry.mood) {
        "muy_bien" -> "😄"
        "bien" -> "😊"
        "neutral" -> "😐"
        "mal" -> "😔"
        "muy_mal" -> "😢"
        else -> "😐"
    }
    
    val moodName = when (entry.mood) {
        "muy_bien" -> "Muy Feliz"
        "bien" -> "Feliz"
        "neutral" -> "Normal"
        "mal" -> "Triste"
        "muy_mal" -> "Muy Triste"
        else -> "Desconocido"
    }
    
    // Formatear fecha
    val date = if (entry.date != null) {
        try {
            val parts = entry.date.split("T")[0].split("-")
            if (parts.size >= 3) {
                "${parts[2]}/${parts[1]}/${parts[0]}"
            } else {
                "Fecha desconocida"
            }
        } catch (e: Exception) {
            "Fecha desconocida"
        }
    } else {
        "Fecha desconocida"
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Emoji
            Text(
                moodEmoji,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(end = 12.dp)
            )
            
            // Información
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    moodName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                if (!entry.notes.isNullOrBlank()) {
                    Text(
                        entry.notes,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
                
                Text(
                    date,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}