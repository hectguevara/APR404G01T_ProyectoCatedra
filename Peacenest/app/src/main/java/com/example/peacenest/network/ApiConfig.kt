package com.example.peacenest.network

/**
 * Configuración de la API
 * 
 * Para conectarte desde un emulador Android:
 * - Usa 10.0.2.2 en lugar de localhost
 * - Esto redirige al localhost de tu máquina
 * 
 * Para un dispositivo físico:
 * - Usa la IP de tu computadora en la red local
 * - Ejemplo: "http://192.168.1.100:8080/api/"
 */
object ApiConfig {
    // URL base del backend
    // IMPORTANTE: Cambia esto según tu dispositivo:
    // - Para EMULADOR: usa "http://10.0.2.2:8080/api/"
    // - Para DISPOSITIVO FÍSICO: usa tu IP local (ej: "http://192.168.1.11:8080/api/")
    const val BASE_URL = "http://192.168.1.11:8080/api/"
    
    // Endpoints
    object Endpoints {
        const val REGISTER = "users/register"
        const val LOGIN = "users/login"
        const val PROFILE = "users/profile"
        const val MEDITATIONS = "meditations"
        const val BREATHING = "breathing"
        const val AUDIOS = "audios"
        const val ARTICLES = "articles"
        const val TRACKING = "tracking"
    }
    
    // Configuración de timeouts
    const val CONNECT_TIMEOUT = 30L // segundos
    const val READ_TIMEOUT = 30L // segundos
    const val WRITE_TIMEOUT = 30L // segundos
}

