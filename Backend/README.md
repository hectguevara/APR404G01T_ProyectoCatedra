# 🌿 PeaceNest Backend

API RESTful desarrollada en Node.js + Firebase para la aplicación móvil **PeaceNest** - Herramientas para la Relajación y Bienestar Mental.

## 📋 Descripción

PeaceNest Backend proporciona endpoints seguros para:
- ✅ Autenticación de usuarios (registro, login, perfil)
- 🧘 Meditaciones guiadas con audio/video
- 🫁 Ejercicios de respiración con temporizador
- 🎵 Biblioteca de audios relajantes
- 📚 Artículos informativos sobre bienestar mental
- 📊 Seguimiento emocional y estadísticas
- 📴 Recursos para modo offline

## 🏗️ Arquitectura del Proyecto

```
Backend/
├── src/
│   ├── config/
│   │   ├── firebase.js          # Configuración Firebase Admin SDK
│   │   ├── jwt.js               # Configuración JWT
│   │   └── swagger.js           # Configuración Swagger
│   ├── controllers/
│   │   ├── userController.js
│   │   ├── meditationController.js
│   │   ├── breathingController.js
│   │   ├── audioController.js
│   │   ├── articleController.js
│   │   ├── trackingController.js
│   │   └── offlineController.js
│   ├── routes/
│   │   ├── userRoutes.js
│   │   ├── meditationRoutes.js
│   │   ├── breathingRoutes.js
│   │   ├── audioRoutes.js
│   │   ├── articleRoutes.js
│   │   ├── trackingRoutes.js
│   │   └── offlineRoutes.js
│   ├── models/
│   │   ├── User.js
│   │   ├── Meditation.js
│   │   ├── BreathingExercise.js
│   │   ├── Audio.js
│   │   ├── Article.js
│   │   └── Tracking.js
│   ├── middleware/
│   │   ├── auth.js              # Autenticación JWT
│   │   ├── errorHandler.js      # Manejo de errores
│   │   └── validators.js        # Validación de datos
│   ├── app.js                   # Configuración de Express
│   └── server.js                # Punto de entrada
├── .env                         # Variables de entorno
├── .gitignore
├── package.json
└── README.md
```

## 🚀 Instalación y Configuración

### Requisitos Previos

- Node.js >= 18.0.0
- npm o yarn
- Cuenta de Firebase con Firestore habilitado
- Proyecto de Firebase configurado

### 1. Clonar el Repositorio

```bash
cd Backend
```

### 2. Instalar Dependencias

```bash
npm install
```

### 3. Configurar Variables de Entorno

Crea un archivo `.env` en la raíz del proyecto Backend con las siguientes variables:

```env
# Configuración del servidor
PORT=8080
NODE_ENV=development

# JWT Configuration
JWT_SECRET=tu_clave_secreta_muy_segura
JWT_EXPIRES_IN=7d

# Firebase Configuration
FIREBASE_PROJECT_ID=tu-proyecto-id
FIREBASE_CLIENT_EMAIL=firebase-adminsdk@tu-proyecto.iam.gserviceaccount.com
FIREBASE_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\nTu_Clave_Privada_Aquí\n-----END PRIVATE KEY-----\n"

# CORS
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080
```

### 4. Configurar Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Crea un nuevo proyecto o selecciona uno existente
3. Habilita Firestore Database
4. Ve a **Project Settings** > **Service Accounts**
5. Genera una nueva clave privada
6. Copia las credenciales al archivo `.env`

### 5. Iniciar el Servidor

#### Desarrollo (con auto-reload)
```bash
npm run dev
```

#### Producción
```bash
npm start
```

El servidor estará disponible en `http://localhost:8080`

## 📚 Documentación de la API

Una vez que el servidor esté ejecutándose, puedes acceder a la documentación interactiva de Swagger en:

```
http://localhost:8080/api/docs
```

## 🔌 Endpoints Principales

### 👤 Usuarios

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/api/users/register` | Registrar nuevo usuario | No |
| POST | `/api/users/login` | Iniciar sesión | No |
| GET | `/api/users/profile` | Obtener perfil | Sí |
| PUT | `/api/users/profile` | Actualizar perfil | Sí |
| DELETE | `/api/users/profile` | Eliminar cuenta | Sí |

### 🧘 Meditaciones

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/meditations` | Listar meditaciones | No |
| GET | `/api/meditations/:id` | Obtener meditación | No |
| GET | `/api/meditations/category/:category` | Por categoría | No |

### 🫁 Ejercicios de Respiración

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/breathing` | Listar ejercicios | No |
| GET | `/api/breathing/:id` | Obtener ejercicio | No |
| POST | `/api/breathing/progress` | Guardar progreso | Sí |
| GET | `/api/breathing/progress` | Ver progreso | Sí |

### 🎵 Audios Relajantes

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/audios` | Listar audios | No |
| GET | `/api/audios/:id` | Obtener audio | No |
| GET | `/api/audios/category/:category` | Por categoría | No |
| GET | `/api/audios/categories` | Listar categorías | No |

### 📚 Artículos

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/articles` | Listar artículos | No |
| GET | `/api/articles/:id` | Obtener artículo | No |
| GET | `/api/articles/search?q=` | Buscar artículos | No |
| GET | `/api/articles/category/:category` | Por categoría | No |

### 📊 Seguimiento Emocional

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/api/tracking` | Crear registro | Sí |
| GET | `/api/tracking` | Listar registros | Sí |
| GET | `/api/tracking/stats` | Ver estadísticas | Sí |
| GET | `/api/tracking/:id` | Obtener registro | Sí |
| PUT | `/api/tracking/:id` | Actualizar registro | Sí |
| DELETE | `/api/tracking/:id` | Eliminar registro | Sí |

### 📴 Modo Offline

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/api/offline/resources` | Todos los recursos | No |
| GET | `/api/offline/meditations` | Meditaciones | No |
| GET | `/api/offline/breathing` | Ejercicios | No |
| GET | `/api/offline/audios` | Audios | No |
| GET | `/api/offline/articles` | Artículos | No |
| GET | `/api/offline/check-updates` | Verificar actualizaciones | No |

## 🔐 Autenticación

La API utiliza **JWT (JSON Web Tokens)** para la autenticación.

### Flujo de Autenticación

1. **Registro**: `POST /api/users/register`
2. **Login**: `POST /api/users/login` → Retorna token JWT
3. **Usar el token**: Incluir en el header de las peticiones protegidas

### Formato del Token

```
Authorization: Bearer <tu_token_jwt>
```

### Ejemplo con cURL

```bash
# Login
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"usuario@example.com","password":"123456"}'

# Usar token
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer tu_token_jwt_aqui"
```

## 🗄️ Estructura de Datos (Firestore)

### Colecciones

#### `users`
```javascript
{
  uid: string,
  email: string,
  password: string (hashed),
  name: string,
  createdAt: timestamp,
  updatedAt: timestamp
}
```

#### `meditations`
```javascript
{
  id: string,
  title: string,
  description: string,
  audioUrl: string,
  duration: number,
  category: string,
  createdAt: timestamp
}
```

#### `breathing_exercises`
```javascript
{
  id: string,
  name: string,
  duration: number,
  instructions: string,
  inhale: number,
  hold: number,
  exhale: number
}
```

#### `audios`
```javascript
{
  id: string,
  title: string,
  audioUrl: string,
  category: string,
  type: string,
  duration: number
}
```

#### `articles`
```javascript
{
  id: string,
  title: string,
  content: string,
  category: string,
  imageUrl: string,
  author: string,
  publishedAt: timestamp
}
```

#### `tracking`
```javascript
{
  id: string,
  userId: string,
  mood: enum ['muy_mal', 'mal', 'neutral', 'bien', 'muy_bien'],
  notes: string,
  activities: array,
  date: timestamp
}
```

## 🧪 Testing

```bash
# Ejecutar tests (cuando estén configurados)
npm test

# Linting
npm run lint
```

## 🛡️ Seguridad

### Implementaciones de Seguridad

- ✅ **JWT**: Autenticación basada en tokens
- ✅ **bcrypt**: Hash seguro de contraseñas (10 rounds)
- ✅ **CORS**: Control de orígenes permitidos
- ✅ **Validación**: Validación de datos con express-validator
- ✅ **Variables de entorno**: Credenciales protegidas en `.env`
- ✅ **Middleware de autenticación**: Protección de rutas privadas

### Recomendaciones de Producción

1. Usar HTTPS (certificado SSL/TLS)
2. Cambiar `JWT_SECRET` por una clave segura y aleatoria
3. Configurar `NODE_ENV=production`
4. Implementar rate limiting
5. Habilitar Firebase Security Rules
6. Usar variables de entorno seguras (Firebase Functions, Heroku, etc.)
7. Implementar logging profesional (Winston, Bunyan)

## 🚢 Despliegue

### Despliegue en Heroku

```bash
# Login en Heroku
heroku login

# Crear aplicación
heroku create peacenest-api

# Configurar variables de entorno
heroku config:set NODE_ENV=production
heroku config:set JWT_SECRET=tu_secret_aqui
heroku config:set FIREBASE_PROJECT_ID=tu_proyecto_id
# ... configurar todas las variables

# Desplegar
git push heroku main
```

### Despliegue en Railway

```bash
# Instalar Railway CLI
npm i -g @railway/cli

# Login
railway login

# Inicializar
railway init

# Desplegar
railway up
```

### Despliegue en Render

1. Conectar repositorio de GitHub
2. Configurar variables de entorno
3. Build command: `npm install`
4. Start command: `npm start`

## 📦 Scripts Disponibles

```bash
npm run dev        # Modo desarrollo con nodemon
npm start          # Modo producción
npm run lint       # Ejecutar ESLint
```

## 🐛 Solución de Problemas

### Error: Firebase Admin SDK no inicializado

**Solución**: Verifica que las credenciales de Firebase en `.env` sean correctas.

### Error: Token inválido o expirado

**Solución**: Genera un nuevo token haciendo login nuevamente.

### Error: Puerto en uso

**Solución**: Cambia el puerto en `.env` o cierra el proceso que está usando el puerto 8080.

## 📝 Requerimientos Cumplidos

### Requerimientos Funcionales

- ✅ **RF01**: Registro e inicio de sesión de usuarios
- ✅ **RF02**: Ejercicios de respiración con temporizador
- ✅ **RF03**: Meditaciones guiadas con audio/video
- ✅ **RF04**: Biblioteca de audios relajantes
- ✅ **RF05**: Artículos informativos y consejos
- ✅ **RF06**: Seguimiento del estado de ánimo
- ✅ **RF07**: Modo offline con descarga de recursos

### Requerimientos No Funcionales

- ✅ **RNF01**: Seguridad (JWT, bcrypt, HTTPS ready)
- ✅ **RNF02**: Usabilidad (API clara, documentada con Swagger)
- ✅ **RNF03**: Compatibilidad (REST API compatible con Kotlin)
- ✅ **RNF04**: Rendimiento (respuestas < 3 segundos)
- ✅ **RNF05**: Portabilidad (desplegable en cualquier servidor Node.js)
- ✅ **RNF06**: Mantenibilidad (código modular y comentado)
- ✅ **RNF07**: Disponibilidad (endpoints offline)

## 🤝 Contribución

Este proyecto es parte del curso **Administración de Proyectos APR404**.

## 📄 Licencia

MIT License

## 👥 Equipo de Desarrollo

**PeaceNest Team** - Proyecto APR404 G01T

## 📞 Soporte

Para preguntas o problemas, contacta al equipo de desarrollo.

---

**Hecho con ❤️ para el bienestar mental**

