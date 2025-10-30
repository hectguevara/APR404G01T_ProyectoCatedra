package com.example.peacenest.network.models

/**
 * Modelos de datos para el seguimiento emocional (Diario)
 */

// Request para crear una entrada del diario
data class TrackingRequest(
    val mood: String,              // 'muy_mal', 'mal', 'neutral', 'bien', 'muy_bien'
    val notes: String? = null,     // Descripción/notas
    val activities: List<String>? = null,  // Actividades realizadas
    val date: String? = null       // Fecha (opcional, se genera en backend si no se envía)
)

// Response al crear/actualizar tracking
data class TrackingResponse(
    val message: String,
    val tracking: TrackingEntry
)

// Entrada individual de tracking
data class TrackingEntry(
    val id: String? = null,
    val userId: String? = null,
    val mood: String? = null,
    val notes: String? = null,
    val activities: List<String>? = null,
    val date: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

// Response para obtener lista de tracking
data class TrackingListResponse(
    val count: Int,
    val tracking: List<TrackingEntry>
)

// Estadísticas del estado de ánimo
data class StatsResponse(
    val stats: TrackingStats
)

data class TrackingStats(
    val totalEntries: Int,
    val moodDistribution: Map<String, Int>,
    val averageMood: Float?,
    val commonActivities: List<ActivityCount>,
    val period: Period? = null
)

data class ActivityCount(
    val activity: String,
    val count: Int
)

data class Period(
    val start: String,
    val end: String
)

