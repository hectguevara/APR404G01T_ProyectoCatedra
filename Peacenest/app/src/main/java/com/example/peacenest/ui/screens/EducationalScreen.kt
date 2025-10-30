package com.example.peacenest.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.peacenest.R
// ⬇️ ELIMINA ESTA LÍNEA ⬇️
// import com.example.peacenest.data.models.Article
import com.example.peacenest.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationalScreen(navController: NavController) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Datos de los artículos
    val articles = listOf(
        Article(
            id = 1,
            title = "Recupera el control ante un ataque de ansiedad",
            intro = "Cuando experimentamos un ataque de ansiedad, es común sentir que perdemos el control del cuerpo y la mente. En este artículo conocerás cómo identificar los síntomas, actuar en el momento adecuado y aplicar estrategias que te ayuden a recuperar la calma de forma eficaz.",
            imageRes = R.drawable.imagen1,
            articleUrl = "https://centromedicoabc.com/revista-digital/recupera-tu-control-ante-un-ataque-de-ansiedad/"
        ),
        Article(
            id = 2,
            title = "Cómo mejorar el sueño cuando sufres de ansiedad",
            intro = "El insomnio y la ansiedad suelen estar profundamente conectados. Dormir mal puede empeorar los síntomas de ansiedad y viceversa. En este artículo descubrirás rutinas, hábitos y consejos prácticos para mejorar tu calidad de sueño y recuperar el equilibrio emocional.",
            imageRes = R.drawable.imagen2,
            articleUrl = "https://medlineplus.gov/spanish/ency/patientinstructions/000853.htm"
        ),
        Article(
            id = 3,
            title = "Cómo calmar tu mente con meditación guiada",
            intro = "Este video guiado te ayudará a calmar la mente, reducir el estrés y encontrar equilibrio emocional mediante técnicas de meditación accesibles para todos.",
            imageRes = R.drawable.imagen3,
            articleUrl = "https://www.youtube.com/watch?v=pDigD65kLpE"
        ),
        Article(
            id = 4,
            title = "Alimentación y ansiedad: cómo lo que comes puede ayudarte",
            intro = "Conoce cómo ciertos hábitos alimenticios pueden aumentar o disminuir tus niveles de ansiedad. En este artículo descubrirás consejos prácticos basados en evidencia.",
            imageRes = R.drawable.imagen4,
            articleUrl = "https://www.unobravo.com/es/blog/ansiedad-por-comer"
        )
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📚 ", style = MaterialTheme.typography.headlineMedium)
                        Text(
                            "Artículos Educativos",
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
                .background(Color(0xFFF0F4F8))
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Text(
                "Recursos Educativos para tu Bienestar",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Text(
                "Descubre artículos y recursos basados en evidencia científica",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            // Lista de artículos
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                articles.forEach { article ->
                    ArticleCard(
                        article = article,
                        onReadMore = {
                            // Aquí iría la lógica para abrir el enlace
                            // Por ejemplo: context.openUrl(article.articleUrl)
                        }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
fun ArticleCard(
    article: Article,
    onReadMore: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Imagen del artículo
            Image(
                painter = painterResource(id = article.imageRes),
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            // Contenido del artículo
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = article.intro,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Botón LEER MÁS
                Button(
                    onClick = onReadMore,
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "LEER MÁS",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

data class Article(
    val id: Int,
    val title: String,
    val intro: String,
    val imageRes: Int,
    val articleUrl: String
)