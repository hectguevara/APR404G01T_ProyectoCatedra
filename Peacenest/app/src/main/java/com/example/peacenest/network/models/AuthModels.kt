package com.example.peacenest.network.models

/**
 * Modelos de datos para autenticación
 */

// Requests
data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String
)

// Responses
data class LoginResponse(
    val message: String,
    val user: User,
    val token: String
)

data class RegisterResponse(
    val message: String,
    val user: User,
    val token: String
)

data class User(
    val uid: String,
    val email: String,
    val name: String,
    val createdAt: String? = null,
    val preferences: Map<String, Any>? = null
)

// Respuesta genérica de error
data class ErrorResponse(
    val message: String,
    val error: String? = null
)

