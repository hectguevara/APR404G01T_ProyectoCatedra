package com.example.peacenest.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Manager para manejar el token de autenticación
 */
object TokenManager {
    private const val PREF_NAME = "peacenest_prefs"
    private const val KEY_TOKEN = "auth_token"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_EMAIL = "user_email"

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    // Guardar datos de autenticación
    fun saveAuthData(token: String, userId: String, userName: String, userEmail: String) {
        sharedPreferences?.edit()?.apply {
            putString(KEY_TOKEN, token)
            putString(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, userName)
            putString(KEY_USER_EMAIL, userEmail)
            apply()
        }
    }

    // Obtener token
    fun getToken(): String? {
        return sharedPreferences?.getString(KEY_TOKEN, null)
    }

    // Obtener ID de usuario
    fun getUserId(): String? {
        return sharedPreferences?.getString(KEY_USER_ID, null)
    }

    // Obtener nombre de usuario
    fun getUserName(): String? {
        return sharedPreferences?.getString(KEY_USER_NAME, null)
    }

    // Obtener email de usuario
    fun getUserEmail(): String? {
        return sharedPreferences?.getString(KEY_USER_EMAIL, null)
    }

    // Verificar si hay sesión activa
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }

    // Limpiar datos de autenticación (logout)
    fun clearAuthData() {
        sharedPreferences?.edit()?.apply {
            remove(KEY_TOKEN)
            remove(KEY_USER_ID)
            remove(KEY_USER_NAME)
            remove(KEY_USER_EMAIL)
            apply()
        }
    }

    // Obtener token con Bearer prefix para las peticiones
    fun getAuthHeader(): String? {
        val token = getToken()
        return if (token != null) "Bearer $token" else null
    }
}

