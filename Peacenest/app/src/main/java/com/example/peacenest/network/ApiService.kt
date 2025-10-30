package com.example.peacenest.network

import com.example.peacenest.network.models.LoginRequest
import com.example.peacenest.network.models.LoginResponse
import com.example.peacenest.network.models.RegisterRequest
import com.example.peacenest.network.models.RegisterResponse
import com.example.peacenest.network.models.TrackingRequest
import com.example.peacenest.network.models.TrackingResponse
import com.example.peacenest.network.models.TrackingListResponse
import com.example.peacenest.network.models.StatsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz del servicio de API
 * Define los endpoints disponibles
 */
interface ApiService {
    
    /**
     * Registro de usuario
     * POST /api/users/register
     */
    @POST(ApiConfig.Endpoints.REGISTER)
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
    
    /**
     * Login de usuario
     * POST /api/users/login
     */
    @POST(ApiConfig.Endpoints.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
    
    /**
     * Obtener perfil de usuario
     * GET /api/users/profile
     * Requiere autenticación
     */
    @GET(ApiConfig.Endpoints.PROFILE)
    suspend fun getProfile(@Header("Authorization") token: String): Response<Any>
    
    // ========== TRACKING / SEGUIMIENTO EMOCIONAL ==========
    
    /**
     * Crear registro de seguimiento emocional
     * POST /api/tracking
     * Requiere autenticación
     */
    @POST(ApiConfig.Endpoints.TRACKING)
    suspend fun createTracking(
        @Header("Authorization") token: String,
        @Body request: TrackingRequest
    ): Response<TrackingResponse>
    
    /**
     * Obtener registros de tracking del usuario
     * GET /api/tracking
     * Requiere autenticación
     */
    @GET(ApiConfig.Endpoints.TRACKING)
    suspend fun getUserTracking(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
        @Query("limit") limit: Int? = null
    ): Response<TrackingListResponse>
    
    /**
     * Obtener estadísticas del estado de ánimo
     * GET /api/tracking/stats
     * Requiere autenticación
     */
    @GET("tracking/stats")
    suspend fun getTrackingStats(
        @Header("Authorization") token: String,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): Response<StatsResponse>
    
    /**
     * Obtener un registro específico
     * GET /api/tracking/:id
     * Requiere autenticación
     */
    @GET("tracking/{id}")
    suspend fun getTrackingById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<TrackingResponse>
    
    /**
     * Actualizar un registro de tracking
     * PUT /api/tracking/:id
     * Requiere autenticación
     */
    @PUT("tracking/{id}")
    suspend fun updateTracking(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body request: TrackingRequest
    ): Response<TrackingResponse>
    
    /**
     * Eliminar un registro de tracking
     * DELETE /api/tracking/:id
     * Requiere autenticación
     */
    @DELETE("tracking/{id}")
    suspend fun deleteTracking(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<Any>
}

