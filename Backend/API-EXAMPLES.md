# 📡 Ejemplos de Uso de la API - PeaceNest

Esta guía contiene ejemplos prácticos de cómo usar cada endpoint de la API.

## 📋 Índice

- [Configuración Inicial](#configuración-inicial)
- [Usuarios](#usuarios)
- [Meditaciones](#meditaciones)
- [Ejercicios de Respiración](#ejercicios-de-respiración)
- [Audios Relajantes](#audios-relajantes)
- [Artículos](#artículos)
- [Seguimiento Emocional](#seguimiento-emocional)
- [Modo Offline](#modo-offline)

## 🔧 Configuración Inicial

### Base URL
```
http://localhost:8080
```

### Headers Comunes
```
Content-Type: application/json
Authorization: Bearer <tu_token_jwt>  # Solo para rutas protegidas
```

---

## 👤 Usuarios

### 1. Registrar Usuario

**Endpoint:** `POST /api/users/register`

**Request:**
```json
{
  "email": "usuario@peacenest.com",
  "password": "123456",
  "name": "María García"
}
```

**Response (201):**
```json
{
  "message": "Usuario registrado exitosamente",
  "user": {
    "uid": "abc123def456",
    "email": "usuario@peacenest.com",
    "name": "María García",
    "createdAt": "2024-01-15T10:30:00.000Z"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**cURL:**
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@peacenest.com",
    "password": "123456",
    "name": "María García"
  }'
```

### 2. Iniciar Sesión

**Endpoint:** `POST /api/users/login`

**Request:**
```json
{
  "email": "usuario@peacenest.com",
  "password": "123456"
}
```

**Response (200):**
```json
{
  "message": "Inicio de sesión exitoso",
  "user": {
    "uid": "abc123def456",
    "email": "usuario@peacenest.com",
    "name": "María García"
  },
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 3. Obtener Perfil

**Endpoint:** `GET /api/users/profile`  
**Requiere Autenticación:** ✅

**Request Headers:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Response (200):**
```json
{
  "user": {
    "uid": "abc123def456",
    "email": "usuario@peacenest.com",
    "name": "María García",
    "createdAt": "2024-01-15T10:30:00.000Z"
  }
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

### 4. Actualizar Perfil

**Endpoint:** `PUT /api/users/profile`  
**Requiere Autenticación:** ✅

**Request:**
```json
{
  "name": "María García Rodríguez"
}
```

---

## 🧘 Meditaciones

### 1. Listar Todas las Meditaciones

**Endpoint:** `GET /api/meditations`

**Response (200):**
```json
{
  "count": 3,
  "meditations": [
    {
      "id": "med123",
      "title": "Meditación para Dormir",
      "description": "Una meditación relajante para conciliar el sueño",
      "audioUrl": "https://example.com/audio.mp3",
      "duration": 15,
      "category": "sueño",
      "createdAt": "2024-01-15T10:00:00.000Z"
    }
  ]
}
```

**cURL:**
```bash
curl -X GET http://localhost:8080/api/meditations
```

### 2. Filtrar por Categoría

**Endpoint:** `GET /api/meditations?category=sueño`

**Query Parameters:**
- `category`: Categoría de meditación (sueño, ansiedad, mindfulness, etc.)
- `duration`: Duración máxima en minutos

**Ejemplo:**
```bash
curl -X GET "http://localhost:8080/api/meditations?category=sueño&duration=20"
```

### 3. Obtener Meditación por ID

**Endpoint:** `GET /api/meditations/:id`

**Response (200):**
```json
{
  "meditation": {
    "id": "med123",
    "title": "Meditación para Dormir",
    "description": "Una meditación relajante para conciliar el sueño",
    "audioUrl": "https://example.com/audio.mp3",
    "duration": 15,
    "category": "sueño"
  }
}
```

---

## 🫁 Ejercicios de Respiración

### 1. Listar Ejercicios

**Endpoint:** `GET /api/breathing`

**Response (200):**
```json
{
  "count": 3,
  "exercises": [
    {
      "id": "breath123",
      "name": "Respiración 4-7-8",
      "duration": 240,
      "instructions": "Inhala por 4 segundos, sostén por 7, exhala por 8",
      "inhale": 4,
      "hold": 7,
      "exhale": 8
    }
  ]
}
```

### 2. Guardar Progreso

**Endpoint:** `POST /api/breathing/progress`  
**Requiere Autenticación:** ✅

**Request:**
```json
{
  "exerciseId": "breath123",
  "completed": true,
  "duration": 240
}
```

**Response (201):**
```json
{
  "message": "Progreso guardado exitosamente",
  "progress": {
    "id": "prog123",
    "userId": "abc123def456",
    "exerciseId": "breath123",
    "completed": true,
    "duration": 240,
    "completedAt": "2024-01-15T14:30:00.000Z"
  }
}
```

### 3. Ver Historial de Progreso

**Endpoint:** `GET /api/breathing/progress?limit=10`  
**Requiere Autenticación:** ✅

**Response (200):**
```json
{
  "count": 5,
  "progress": [
    {
      "id": "prog123",
      "exerciseId": "breath123",
      "completed": true,
      "completedAt": "2024-01-15T14:30:00.000Z"
    }
  ]
}
```

---

## 🎵 Audios Relajantes

### 1. Listar Audios

**Endpoint:** `GET /api/audios`

**Query Parameters:**
- `category`: naturaleza, musica, ambientes
- `type`: ambiente, instrumental

**Response (200):**
```json
{
  "count": 4,
  "audios": [
    {
      "id": "audio123",
      "title": "Lluvia Suave",
      "audioUrl": "https://example.com/lluvia.mp3",
      "category": "naturaleza",
      "type": "ambiente",
      "duration": 1800
    }
  ]
}
```

### 2. Obtener Categorías

**Endpoint:** `GET /api/audios/categories`

**Response (200):**
```json
{
  "count": 3,
  "categories": ["naturaleza", "musica", "ambientes"]
}
```

---

## 📚 Artículos

### 1. Listar Artículos

**Endpoint:** `GET /api/articles`

**Response (200):**
```json
{
  "count": 4,
  "articles": [
    {
      "id": "art123",
      "title": "5 Técnicas de Respiración para Reducir el Estrés",
      "content": "La respiración consciente es una herramienta...",
      "category": "técnicas",
      "author": "Dr. María Pérez",
      "publishedAt": "2024-01-15T10:00:00.000Z"
    }
  ]
}
```

### 2. Buscar Artículos

**Endpoint:** `GET /api/articles/search?q=respiración`

**Response (200):**
```json
{
  "query": "respiración",
  "count": 2,
  "articles": [...]
}
```

### 3. Obtener Artículo por ID

**Endpoint:** `GET /api/articles/:id`

---

## 📊 Seguimiento Emocional

### 1. Crear Registro

**Endpoint:** `POST /api/tracking`  
**Requiere Autenticación:** ✅

**Request:**
```json
{
  "mood": "bien",
  "notes": "Me siento relajado después de la meditación",
  "activities": ["meditación", "respiración", "música"]
}
```

**Valores válidos para `mood`:**
- `muy_mal`
- `mal`
- `neutral`
- `bien`
- `muy_bien`

**Response (201):**
```json
{
  "message": "Registro de seguimiento creado exitosamente",
  "tracking": {
    "id": "track123",
    "userId": "abc123def456",
    "mood": "bien",
    "notes": "Me siento relajado después de la meditación",
    "activities": ["meditación", "respiración", "música"],
    "date": "2024-01-15T15:00:00.000Z"
  }
}
```

### 2. Obtener Registros

**Endpoint:** `GET /api/tracking`  
**Requiere Autenticación:** ✅

**Query Parameters:**
- `startDate`: Fecha inicio (ISO 8601)
- `endDate`: Fecha fin (ISO 8601)
- `limit`: Número de registros (default: 30)

**Ejemplo:**
```bash
curl -X GET "http://localhost:8080/api/tracking?startDate=2024-01-01&limit=10" \
  -H "Authorization: Bearer TU_TOKEN"
```

**Response (200):**
```json
{
  "count": 10,
  "tracking": [
    {
      "id": "track123",
      "mood": "bien",
      "notes": "Me siento relajado",
      "date": "2024-01-15T15:00:00.000Z"
    }
  ]
}
```

### 3. Obtener Estadísticas

**Endpoint:** `GET /api/tracking/stats`  
**Requiere Autenticación:** ✅

**Query Parameters:**
- `startDate`: Fecha inicio
- `endDate`: Fecha fin

**Response (200):**
```json
{
  "stats": {
    "totalEntries": 30,
    "moodDistribution": {
      "muy_bien": 8,
      "bien": 12,
      "neutral": 7,
      "mal": 2,
      "muy_mal": 1
    },
    "averageMood": 3.8,
    "commonActivities": [
      { "activity": "meditación", "count": 25 },
      { "activity": "respiración", "count": 20 },
      { "activity": "música", "count": 15 }
    ],
    "period": {
      "start": "2024-01-01T00:00:00.000Z",
      "end": "2024-01-30T23:59:59.000Z"
    }
  }
}
```

---

## 📴 Modo Offline

### 1. Obtener Todos los Recursos

**Endpoint:** `GET /api/offline/resources`

**Response (200):**
```json
{
  "message": "Recursos offline obtenidos exitosamente",
  "version": "1.0.0",
  "lastUpdated": "2024-01-15T10:00:00.000Z",
  "estimatedSize": {
    "meditations": 15,
    "audios": 12,
    "exercises": 0.3,
    "articles": 2,
    "total": 29.3,
    "unit": "MB"
  },
  "resources": {
    "meditations": {
      "count": 3,
      "items": [...]
    },
    "breathingExercises": {
      "count": 3,
      "items": [...]
    },
    "audios": {
      "count": 4,
      "items": [...]
    },
    "articles": {
      "count": 4,
      "items": [...]
    }
  }
}
```

### 2. Verificar Actualizaciones

**Endpoint:** `GET /api/offline/check-updates?version=1.0.0`

**Response (200):**
```json
{
  "currentVersion": "1.0.0",
  "clientVersion": "1.0.0",
  "updateAvailable": false,
  "lastUpdated": "2024-01-15T10:00:00.000Z"
}
```

---

## ❌ Manejo de Errores

### Errores Comunes

#### 400 - Bad Request
```json
{
  "error": "Error de validación",
  "code": "VALIDATION_ERROR",
  "details": [
    {
      "field": "email",
      "message": "Debe proporcionar un email válido"
    }
  ]
}
```

#### 401 - No Autorizado
```json
{
  "error": "Acceso denegado. No se proporcionó token de autenticación.",
  "code": "NO_TOKEN"
}
```

#### 403 - Prohibido
```json
{
  "error": "Token inválido o expirado",
  "code": "INVALID_TOKEN"
}
```

#### 404 - No Encontrado
```json
{
  "error": "Recurso no encontrado",
  "code": "NOT_FOUND"
}
```

#### 500 - Error del Servidor
```json
{
  "error": "Error interno del servidor",
  "code": "INTERNAL_ERROR"
}
```

---

## 🔐 Autenticación en Postman

### 1. Hacer Login
1. Envía una petición a `POST /api/users/login`
2. Copia el `token` de la respuesta

### 2. Configurar Token
1. Ve a la pestaña **Authorization**
2. Selecciona **Bearer Token**
3. Pega el token copiado

### 3. Alternativa: Variables de Entorno
```javascript
// En Tests (después del login):
pm.environment.set("token", pm.response.json().token);

// Luego usa en Authorization:
{{token}}
```

---

## 🚀 Ejemplo Completo: Flujo de Usuario

```bash
# 1. Registrar usuario
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@peacenest.com","password":"123456","name":"Test User"}'

# 2. Obtener token (guarda el token de la respuesta)
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

# 3. Ver meditaciones disponibles
curl -X GET http://localhost:8080/api/meditations

# 4. Registrar estado de ánimo
curl -X POST http://localhost:8080/api/tracking \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"mood":"bien","notes":"Me siento genial","activities":["meditación"]}'

# 5. Ver estadísticas
curl -X GET http://localhost:8080/api/tracking/stats \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📱 Integración con Kotlin (Android)

### Ejemplo con Retrofit

```kotlin
// API Interface
interface PeaceNestAPI {
    @POST("users/register")
    suspend fun register(@Body user: RegisterRequest): Response<UserResponse>
    
    @POST("users/login")
    suspend fun login(@Body credentials: LoginRequest): Response<UserResponse>
    
    @GET("meditations")
    suspend fun getMeditations(): Response<MeditationsResponse>
    
    @POST("tracking")
    suspend fun createTracking(
        @Header("Authorization") token: String,
        @Body tracking: TrackingRequest
    ): Response<TrackingResponse>
}

// Uso
val token = "Bearer $userToken"
val response = api.createTracking(token, trackingData)
```

---

**¡Listo! Ya tienes todos los ejemplos necesarios para usar la API de PeaceNest.** 🌿

Para más detalles, visita la documentación interactiva en:
```
http://localhost:8080/api/docs
```

