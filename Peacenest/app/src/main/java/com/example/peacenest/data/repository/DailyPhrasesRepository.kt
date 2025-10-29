package com.example.peacenest.data.repository

import com.example.peacenest.data.models.DailyPhrase
import kotlin.random.Random

object DailyPhrasesRepository {
    
    private val phrases = listOf(
        DailyPhrase(1, "Respira profundamente, estás haciendo lo mejor que puedes.", "PeaceNest"),
        DailyPhrase(2, "No tienes que controlar tus pensamientos. Solo deja de permitir que ellos te controlen.", "Dan Millman"),
        DailyPhrase(3, "La ansiedad no puede controlar tu vida si aprendes a observarla y no a reaccionar a ella.", "PeaceNest"),
        DailyPhrase(4, "Está bien no estar bien. Permítete sentir y luego sigue adelante.", "PeaceNest"),
        DailyPhrase(5, "Tu valor no disminuye por las emociones que enfrentas.", "PeaceNest"),
        DailyPhrase(6, "A veces la calma comienza con una simple respiración consciente.", "PeaceNest"),
        DailyPhrase(7, "No estás solo. Muchas personas enfrentan lo que tú enfrentas y salen adelante.", "PeaceNest"),
        DailyPhrase(8, "Tomarte un descanso también es avanzar.", "PeaceNest"),
        DailyPhrase(9, "Cada día es una nueva oportunidad para comenzar a sanar.", "PeaceNest"),
        DailyPhrase(10, "Confía en el proceso. La recuperación no es lineal, pero es posible.", "PeaceNest"),
        DailyPhrase(11, "La vida es un viaje, no un destino. Disfruta cada paso del camino.", "PeaceNest"),
        DailyPhrase(12, "No te compares con los demás. Cada uno tiene su propio camino.", "PeaceNest"),
        DailyPhrase(13, "La paz interior comienza en el momento en que decides no permitir que otra persona o evento controle tus emociones.", "PeaceNest"),
        DailyPhrase(14, "La felicidad no es la ausencia de problemas, sino la habilidad de lidiar con ellos.", "PeaceNest"),
        DailyPhrase(15, "Recuerda que está bien pedir ayuda. No tienes que enfrentar esto solo.", "PeaceNest")
    )
    
    /**
     * Obtiene una frase aleatoria diferente cada vez
     */
    fun getRandomPhrase(): DailyPhrase {
        return phrases.random()
    }
    
    /**
     * Obtiene todas las frases
     */
    fun getAllPhrases(): List<DailyPhrase> {
        return phrases
    }
}

