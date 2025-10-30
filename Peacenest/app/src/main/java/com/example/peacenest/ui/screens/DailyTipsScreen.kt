package com.example.peacenest.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peacenest.navigation.Routes
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyTipsScreen(navController: NavController) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var currentQuote by remember { mutableStateOf<Quote?>(null) }
    var showSavedConfirmation by remember { mutableStateOf(false) }

    // Datos de las frases
    val quotes = remember {
        listOf(
            Quote(
                id = 1,
                text = "Respira profundamente, estás haciendo lo mejor que puedes.",
                author = "PeaceNest"
            ),
            Quote(
                id = 2,
                text = "No tienes que controlar tus pensamientos. Solo deja de permitir que ellos te controlen.",
                author = "Dan Millman"
            ),
            Quote(
                id = 3,
                text = "La ansiedad no puede controlar tu vida si aprendes a observarla y no a reaccionar a ella.",
                author = "PeaceNest"
            ),
            Quote(
                id = 4,
                text = "Está bien no estar bien. Permítete sentir y luego sigue adelante.",
                author = "PeaceNest"
            ),
            Quote(
                id = 5,
                text = "Tu valor no disminuye por las emociones que enfrentas.",
                author = "PeaceNest"
            ),
            Quote(
                id = 6,
                text = "A veces la calma comienza con una simple respiración consciente.",
                author = "PeaceNest"
            ),
            Quote(
                id = 7,
                text = "No estás solo. Muchas personas enfrentan lo que tú enfrentas y salen adelante.",
                author = "PeaceNest"
            ),
            Quote(
                id = 8,
                text = "Tomarte un descanso también es avanzar.",
                author = "PeaceNest"
            ),
            Quote(
                id = 9,
                text = "Cada día es una nueva oportunidad para comenzar a sanar.",
                author = "PeaceNest"
            ),
            Quote(
                id = 10,
                text = "Confía en el proceso. La recuperación no es lineal, pero es posible.",
                author = "PeaceNest"
            ),
            Quote(
                id = 11,
                text = "La vida es un viaje, no un destino. Disfruta cada paso del camino.",
                author = "PeaceNest"
            ),
            Quote(
                id = 12,
                text = "No te compares con los demás. Cada uno tiene su propio camino.",
                author = "PeaceNest"
            ),
            Quote(
                id = 13,
                text = "La paz interior comienza en el momento en que decides no permitir que otra persona o evento controle tus emociones.",
                author = "PeaceNest"
            ),
            Quote(
                id = 14,
                text = "La felicidad no es la ausencia de problemas, sino la habilidad de lidiar con ellos.",
                author = "PeaceNest"
            ),
            Quote(
                id = 15,
                text = "Recuerda que está bien pedir ayuda. No tienes que enfrentar esto solo.",
                author = "PeaceNest"
            )
        )
    }

    // Cargar una frase aleatoria al inicio
    LaunchedEffect(Unit) {
        currentQuote = quotes.random()
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
            title = { Text("Cerrar Sesión", fontWeight = FontWeight.Bold) },
            text = { Text("¿Estás seguro de que deseas cerrar sesión?") },
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
                ) { Text("Cerrar Sesión") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Snackbar de confirmación de guardado
    if (showSavedConfirmation) {
        LaunchedEffect(showSavedConfirmation) {
            // Auto-ocultar después de 2 segundos
            kotlinx.coroutines.delay(2000)
            showSavedConfirmation = false
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Snackbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Guardado en favoritos",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("💭 ", style = MaterialTheme.typography.headlineMedium)
                        Text(
                            "Consejos Diarios",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
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
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                "Inspiración para tu día",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = TextAlign.Center
            )

            Text(
                "Descubre una nueva perspectiva cada día",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )

            // Tarjeta de la frase
            currentQuote?.let { quote ->
                QuoteCard(
                    quote = quote,
                    onSaveClick = {
                        // Aquí iría la lógica para guardar en favoritos
                        showSavedConfirmation = true
                    },
                    onRefreshClick = {
                        // Obtener nueva frase aleatoria (excluyendo la actual)
                        val newQuotes = quotes.filter { it.id != quote.id }
                        currentQuote = newQuotes.random()
                    }
                )
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun QuoteCard(
    quote: Quote,
    onSaveClick: () -> Unit,
    onRefreshClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icono decorativo
            Card(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "💭",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Texto de la frase
            Text(
                text = "\"${quote.text}\"",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Autor
            Text(
                text = "— ${quote.author}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botón de nueva frase
                Button(
                    onClick = onRefreshClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(
                        Icons.Default.Refresh,
                        contentDescription = "Nueva frase",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Nueva Frase")
                }

                // Botón de guardar
                Button(
                    onClick = onSaveClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Guardar favorito",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Guardar")
                }
            }
        }
    }
}

// Data class para las frases
data class Quote(
    val id: Int,
    val text: String,
    val author: String
)