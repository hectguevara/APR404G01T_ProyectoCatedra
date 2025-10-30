package com.example.peacenest.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationExerciseScreen(
    navController: NavController,
    meditationId: String = "1"
) {
    // Configuración según la meditación
    val (meditationName, durationMinutes, phases) = when (meditationId) {
        "1" -> MeditationConfig("Relajación Profunda", 15, listOf(
            "Cierra tus ojos y respira profundamente",
            "Relaja tus hombros y suelta la tensión",
            "Siente tu cuerpo hundirse en calma",
            "Libera cualquier pensamiento"
        ))
        "2" -> MeditationConfig("Mindfulness para Ansiedad", 10, listOf(
            "Observa tu respiración sin juzgar",
            "Reconoce tus pensamientos y déjalos ir",
            "Ancla tu atención en el presente",
            "Acepta lo que sientes con compasión"
        ))
        "3" -> MeditationConfig("Gratitud y Positividad", 12, listOf(
            "Piensa en algo por lo que estás agradecido",
            "Siente la calidez de ese sentimiento",
            "Expande esa gratitud a más áreas",
            "Sonríe y abraza ese sentimiento"
        ))
        "4" -> MeditationConfig("Meditación de Amor y Compasión", 20, listOf(
            "Dirige amor hacia ti mismo",
            "Extiende ese amor a alguien querido",
            "Comparte compasión con alguien difícil",
            "Expande amor a todos los seres"
        ))
        "5" -> MeditationConfig("Body Scan Consciente", 18, listOf(
            "Escanea tu cuerpo desde los pies",
            "Nota sensaciones sin juzgar",
            "Avanza lentamente hacia arriba",
            "Siente tu cuerpo como un todo"
        ))
        else -> MeditationConfig("Relajación Profunda", 15, listOf(
            "Cierra tus ojos y respira profundamente",
            "Relaja tus hombros y suelta la tensión",
            "Siente tu cuerpo hundirse en calma",
            "Libera cualquier pensamiento"
        ))
    }
    
    var isActive by remember { mutableStateOf(false) }
    var currentPhaseIndex by remember { mutableStateOf(0) }
    var remainingSeconds by remember { mutableStateOf(durationMinutes * 60) }
    var isPaused by remember { mutableStateOf(false) }
    
    val phaseTime = (durationMinutes * 60) / phases.size
    
    // Animación del círculo de pulso
    val infiniteTransition = rememberInfiniteTransition(label = "meditation")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    // Lógica del temporizador
    LaunchedEffect(isActive, isPaused) {
        if (isActive && !isPaused) {
            while (remainingSeconds > 0 && isActive && !isPaused) {
                delay(1000)
                remainingSeconds--
                
                // Cambiar de fase según el tiempo transcurrido
                val elapsedSeconds = (durationMinutes * 60) - remainingSeconds
                val newPhaseIndex = (elapsedSeconds / phaseTime).coerceAtMost(phases.size - 1)
                if (newPhaseIndex != currentPhaseIndex) {
                    currentPhaseIndex = newPhaseIndex
                }
            }
            
            if (remainingSeconds <= 0) {
                isActive = false
                isPaused = false
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(meditationName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver")
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
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Temporizador
            Text(
                "Tiempo restante",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val minutes = remainingSeconds / 60
            val seconds = remainingSeconds % 60
            Text(
                String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 64.sp
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Círculo animado con fase actual
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .scale(if (isActive && !isPaused) scale else 1f)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "🧘",
                        fontSize = 42.sp
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    if (isActive) {
                        Text(
                            phases[currentPhaseIndex],
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    } else {
                        Text(
                            "Preparado para comenzar",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Progreso de fases
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Fases de la Meditación:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    phases.forEachIndexed { index, phase ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                if (index == currentPhaseIndex && isActive) "▶️" else "⚪",
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                phase,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (index == currentPhaseIndex && isActive) FontWeight.Bold else FontWeight.Normal,
                                color = if (index == currentPhaseIndex && isActive) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Botones de control
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                if (!isActive) {
                    Button(
                        onClick = { 
                            isActive = true
                            isPaused = false
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            "Comenzar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Button(
                        onClick = { isPaused = !isPaused },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isPaused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Text(
                            if (isPaused) "Continuar" else "Pausar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    OutlinedButton(
                        onClick = { 
                            isActive = false
                            isPaused = false
                            remainingSeconds = durationMinutes * 60
                            currentPhaseIndex = 0
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            "Reiniciar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Mensaje de completado
            if (!isActive && remainingSeconds <= 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "¡Excelente sesión! 🎉",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Data class para configuración de meditaciones
data class MeditationConfig(
    val name: String,
    val durationMinutes: Int,
    val phases: List<String>
)

