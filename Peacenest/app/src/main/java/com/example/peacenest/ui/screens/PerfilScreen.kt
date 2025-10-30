package com.example.peacenest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peacenest.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(navController: NavController) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var editando by remember { mutableStateOf(false) }

    // Datos del perfil
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var emocionFavorita by remember { mutableStateOf("Feliz 😄") }
    var favoritos by remember { mutableStateOf(listOf(
        "Respira profundo en momentos de estrés.",
        "Tómate 5 minutos para ti cada mañana.",
        "Escribe una cosa buena de tu día."
    )) }
    var insignias by remember { mutableStateOf(listOf("Principiante", "7 días seguidos")) }

    val emocionesDisponibles = listOf(
        "Feliz 😄",
        "Triste 😢",
        "Enojado 😠",
        "Relajado 😌",
        "Ansioso 😰",
        "Motivado 💪"
    )

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

    // Diálogo de confirmación de reiniciar datos
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            icon = {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            title = {
                Text("Reiniciar Todo", fontWeight = FontWeight.Bold)
            },
            text = {
                Text("¿Estás seguro de que quieres reiniciar todos tus datos? Esta acción no se puede deshacer.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        // Aquí iría la lógica para reiniciar datos
                        nombre = ""
                        descripcion = ""
                        emocionFavorita = "Feliz 😄"
                        favoritos = listOf(
                            "Respira profundo en momentos de estrés.",
                            "Tómate 5 minutos para ti cada mañana.",
                            "Escribe una cosa buena de tu día."
                        )
                        insignias = listOf("Principiante")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Reiniciar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
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
                        "Mi Perfil",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        "Gestiona tu información personal",
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
                // Foto de perfil
                ProfilePhotoSection(
                    editando = editando,
                    onEditPhoto = { /* Lógica para seleccionar foto */ }
                )

                Spacer(Modifier.height(24.dp))

                // Información personal
                PersonalInfoCard(
                    editando = editando,
                    nombre = nombre,
                    onNombreChange = { nombre = it },
                    descripcion = descripcion,
                    onDescripcionChange = { descripcion = it },
                    emocionFavorita = emocionFavorita,
                    onEmocionChange = { emocionFavorita = it },
                    emocionesDisponibles = emocionesDisponibles
                )

                Spacer(Modifier.height(16.dp))

                // Consejos favoritos
                FavoritesCard(
                    editando = editando,
                    favoritos = favoritos,
                    onFavoritosChange = { favoritos = it }
                )

                Spacer(Modifier.height(16.dp))

                // Insignias
                BadgesCard(insignias = insignias)

                Spacer(Modifier.height(24.dp))

                // Botones de acción
                ActionButtons(
                    editando = editando,
                    onEditToggle = { editando = !editando },
                    onReset = { showResetDialog = true },
                    onLogout = { showLogoutDialog = true }
                )
            }
        }
    }
}

@Composable
fun ProfilePhotoSection(editando: Boolean, onEditPhoto: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            // Placeholder de foto
            Card(
                modifier = Modifier.size(120.dp),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Foto de perfil",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.size(40.dp)
                    )
                }
            }

            // Badge de edición - USANDO EDIT QUE SÍ EXISTE
            if (editando) {
                Card(
                    modifier = Modifier
                        .size(32.dp)
                        .align(Alignment.BottomEnd),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Edit, // ✅ Este ícono SÍ existe
                            contentDescription = "Editar foto",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PersonalInfoCard(
    editando: Boolean,
    nombre: String,
    onNombreChange: (String) -> Unit,
    descripcion: String,
    onDescripcionChange: (String) -> Unit,
    emocionFavorita: String,
    onEmocionChange: (String) -> Unit,
    emocionesDisponibles: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Nombre
            ProfileField(
                label = "Nombre",
                value = nombre,
                editando = editando,
                onValueChange = onNombreChange,
                placeholder = "Ingresa tu nombre"
            )

            Spacer(Modifier.height(16.dp))

            // Descripción
            ProfileField(
                label = "Descripción",
                value = descripcion,
                editando = editando,
                onValueChange = onDescripcionChange,
                placeholder = "Cuéntanos sobre ti...",
                multiline = true
            )

            Spacer(Modifier.height(16.dp))

            // Emoción favorita
            Text(
                "Emoción Favorita",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))

            if (editando) {
                OutlinedTextField(
                    value = emocionFavorita,
                    onValueChange = onEmocionChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
            } else {
                Text(
                    emocionFavorita,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    editando: Boolean,
    onValueChange: (String) -> Unit,
    placeholder: String,
    multiline: Boolean = false
) {
    Column {
        Text(
            label,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(8.dp))

        if (editando) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        placeholder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                ),
                singleLine = !multiline
            )
        } else {
            Text(
                value.ifEmpty { "No definido" },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun FavoritesCard(
    editando: Boolean,
    favoritos: List<String>,
    onFavoritosChange: (List<String>) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Tus Consejos Favoritos",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            favoritos.forEachIndexed { index, favorito ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        "• ",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    if (editando) {
                        OutlinedTextField(
                            value = favorito,
                            onValueChange = { newValue ->
                                val nuevosFavoritos = favoritos.toMutableList()
                                nuevosFavoritos[index] = newValue
                                onFavoritosChange(nuevosFavoritos)
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            singleLine = true
                        )
                    } else {
                        Text(
                            favorito,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BadgesCard(insignias: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Tus Insignias",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            if (insignias.isEmpty()) {
                Text(
                    "Aún no tienes insignias. Sigue usando la app para ganarlas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Usamos Row con wrap para las insignias
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        insignias.forEach { insignia ->
                            Card(
                                modifier = Modifier.padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Text(
                                    "🏅 $insignia",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionButtons(
    editando: Boolean,
    onEditToggle: () -> Unit,
    onReset: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Botón editar/guardar
        Button(
            onClick = onEditToggle,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            )
        ) {
            Icon(
                if (editando) Icons.Default.Check else Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                if (editando) "Guardar Cambios" else "Editar Perfil",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Botón reiniciar
        OutlinedButton(
            onClick = onReset,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Reiniciar Todo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        // Botón cerrar sesión
        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                "Cerrar Sesión",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}