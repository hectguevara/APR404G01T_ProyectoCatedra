package com.example.peacenest.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreathingExerciseScreen(
    navController: NavController,
    techniqueId: String = "1"
) {
    // Configuración según la técnica
    val (techniqueName, inhaleTime, holdTime, exhaleTime, targetCycles) = when (techniqueId) {
        "1" -> TechniqueConfig("Respiración 4-7-8", 4, 7, 8, 5)
        "2" -> TechniqueConfig("Respiración Cuadrada", 4, 4, 4, 6) // Box Breathing: todo igual
        "3" -> TechniqueConfig("Respiración Diafragmática", 6, 2, 8, 5) // Respiración profunda y lenta
        "4" -> TechniqueConfig("Respiración Alternada", 4, 4, 4, 8) // Nadi Shodhana
        "5" -> TechniqueConfig("Respiración de Fuego", 1, 0, 1, 10) // Kapalabhati: rápida y energizante
        else -> TechniqueConfig("Respiración 4-7-8", 4, 7, 8, 5)
    }
    
    var isActive by remember { mutableStateOf(false) }
    var currentPhase by remember { mutableStateOf("Preparado") }
    var remainingTime by remember { mutableStateOf(0) }
    var cycleCount by remember { mutableStateOf(0) }
    
    // Animación del círculo
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isActive && currentPhase == "Inhala") 1.5f else if (currentPhase == "Exhala") 0.7f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scale"
    )
    
    // Lógica del temporizador
    LaunchedEffect(isActive) {
        if (isActive && cycleCount < targetCycles) {
            while (cycleCount < targetCycles && isActive) {
                // Fase 1: Inhalar
                currentPhase = "Inhala"
                for (i in inhaleTime downTo 1) {
                    remainingTime = i
                    delay(1000)
                }
                
                // Fase 2: Mantener (solo si holdTime > 0)
                if (holdTime > 0) {
                    currentPhase = "Mantén"
                    for (i in holdTime downTo 1) {
                        remainingTime = i
                        delay(1000)
                    }
                }
                
                // Fase 3: Exhalar
                currentPhase = "Exhala"
                for (i in exhaleTime downTo 1) {
                    remainingTime = i
                    delay(1000)
                }
                
                cycleCount++
                
                if (cycleCount >= targetCycles) {
                    currentPhase = "¡Completado!"
                    isActive = false
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(techniqueName, fontWeight = FontWeight.Bold) },
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
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Contador de ciclos
            Text(
                "Ciclo ${cycleCount + 1} de $targetCycles",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Círculo animado
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .scale(if (isActive) scale else 1f)
                    .background(
                        color = when (currentPhase) {
                            "Inhala" -> MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                            "Mantén" -> MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)
                            "Exhala" -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        currentPhase,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 32.sp
                    )
                    
                    if (isActive) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "$remainingTime",
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 72.sp
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Instrucciones
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
                        "Instrucciones:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    InstructionRow("1️⃣", "Inhala", "$inhaleTime segundos")
                    if (holdTime > 0) {
                        InstructionRow("2️⃣", "Mantén", "$holdTime segundos")
                    }
                    InstructionRow(if (holdTime > 0) "3️⃣" else "2️⃣", "Exhala", "$exhaleTime segundos")
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
            
            // Botones de control
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (!isActive && cycleCount == 0) {
                    Button(
                        onClick = { isActive = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
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
                } else if (isActive) {
                    Button(
                        onClick = { 
                            isActive = false
                            currentPhase = "Pausado"
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Text(
                            "Pausar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else if (cycleCount < targetCycles) {
                    Button(
                        onClick = { isActive = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .padding(horizontal = 32.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            "Continuar",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "¡Excelente trabajo! 🎉",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                cycleCount = 0
                                currentPhase = "Preparado"
                                isActive = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(horizontal = 32.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                "Repetir",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InstructionRow(emoji: String, phase: String, duration: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(emoji, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                phase,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
        Text(
            duration,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// Data class para configuración de técnicas
data class TechniqueConfig(
    val name: String,
    val inhaleTime: Int,
    val holdTime: Int,
    val exhaleTime: Int,
    val cycles: Int
)
