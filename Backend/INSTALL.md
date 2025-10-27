# 📦 Guía de Instalación - PeaceNest Backend

Esta guía te ayudará a configurar el backend de PeaceNest paso a paso.

## ✅ Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **Node.js** (versión 18 o superior)
- **npm** (viene con Node.js)
- **Git** (opcional, para clonar el repositorio)
- **Cuenta de Firebase** (gratuita)

### Verificar instalaciones

```bash
node --version    # Debe mostrar v18.x.x o superior
npm --version     # Debe mostrar 8.x.x o superior
```

## 🔥 Paso 1: Configurar Firebase

### 1.1 Crear proyecto en Firebase

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Haz clic en **"Agregar proyecto"** o **"Add project"**
3. Ingresa el nombre: `peacenest` (o el que prefieras)
4. Desactiva Google Analytics (opcional para desarrollo)
5. Haz clic en **"Crear proyecto"**

### 1.2 Habilitar Firestore

1. En el menú lateral, ve a **"Firestore Database"**
2. Haz clic en **"Crear base de datos"**
3. Selecciona **"Iniciar en modo de prueba"** (para desarrollo)
4. Elige la ubicación más cercana (ej: `southamerica-east1`)
5. Haz clic en **"Habilitar"**

### 1.3 Obtener credenciales

1. Ve a **Configuración del proyecto** (ícono de engranaje) > **Cuentas de servicio**
2. Selecciona **"Node.js"** como lenguaje
3. Haz clic en **"Generar nueva clave privada"**
4. Se descargará un archivo JSON con tus credenciales
5. **¡IMPORTANTE!**: Guarda este archivo en un lugar seguro y **NUNCA** lo subas a Git

## 💻 Paso 2: Instalar el Backend

### 2.1 Navegar a la carpeta Backend

```bash
cd Backend
```

### 2.2 Instalar dependencias

```bash
npm install
```

Esto instalará todos los paquetes necesarios:
- express
- firebase-admin
- jsonwebtoken
- bcrypt
- cors
- dotenv
- swagger-ui-express
- express-validator
- morgan
- nodemon (dev)
- eslint (dev)
- prettier (dev)

## ⚙️ Paso 3: Configurar Variables de Entorno

### 3.1 Crear archivo .env

En la carpeta `Backend`, crea un archivo llamado `.env`:

```bash
# En Windows (PowerShell)
New-Item .env

# En macOS/Linux
touch .env
```

### 3.2 Editar el archivo .env

Abre el archivo `.env` con tu editor de texto y copia lo siguiente:

```env
# Configuración del servidor
PORT=8080
NODE_ENV=development

# JWT Configuration
JWT_SECRET=mi_clave_secreta_super_segura_123456789
JWT_EXPIRES_IN=7d

# Firebase Configuration
FIREBASE_PROJECT_ID=tu-proyecto-id
FIREBASE_CLIENT_EMAIL=firebase-adminsdk@tu-proyecto.iam.gserviceaccount.com
FIREBASE_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\nPEGA_TU_CLAVE_PRIVADA_AQUI\n-----END PRIVATE KEY-----\n"

# CORS
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080
```

### 3.3 Completar con tus credenciales de Firebase

Abre el archivo JSON que descargaste de Firebase y copia los valores:

- **FIREBASE_PROJECT_ID**: Valor de `project_id`
- **FIREBASE_CLIENT_EMAIL**: Valor de `client_email`
- **FIREBASE_PRIVATE_KEY**: Valor de `private_key` (¡copia TODO incluyendo `-----BEGIN` y `-----END`!)

**Ejemplo**:

```env
FIREBASE_PROJECT_ID=peacenest-12345
FIREBASE_CLIENT_EMAIL=firebase-adminsdk-abc12@peacenest-12345.iam.gserviceaccount.com
FIREBASE_PRIVATE_KEY="-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BA...(resto de la clave)\n-----END PRIVATE KEY-----\n"
```

### 3.4 Generar una clave JWT segura (opcional pero recomendado)

```bash
# En Node.js
node -e "console.log(require('crypto').randomBytes(64).toString('hex'))"
```

Copia el resultado y úsalo como `JWT_SECRET`.

## 🚀 Paso 4: Iniciar el Servidor

### 4.1 Modo desarrollo (con auto-reload)

```bash
npm run dev
```

Verás algo como:

```
╔══════════════════════════════════════════════════════╗
║                                                      ║
║              🌿 PEACENEST API 🌿                     ║
║                                                      ║
╚══════════════════════════════════════════════════════╝

✅ Servidor ejecutándose en el puerto 8080
🌍 Entorno: development
📚 Documentación: http://localhost:8080/api/docs
🏥 Health Check: http://localhost:8080/health
```

### 4.2 Modo producción

```bash
npm start
```

## 🧪 Paso 5: Probar la API

### 5.1 Verificar que el servidor funciona

Abre tu navegador y ve a:
```
http://localhost:8080
```

Deberías ver un JSON con información de bienvenida.

### 5.2 Verificar documentación Swagger

Ve a:
```
http://localhost:8080/api/docs
```

Aquí podrás ver y probar todos los endpoints disponibles.

### 5.3 Probar registro de usuario

Usa Postman, Thunder Client, o cURL:

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@peacenest.com",
    "password": "123456",
    "name": "Usuario de Prueba"
  }'
```

Si todo funciona correctamente, recibirás una respuesta con el usuario creado y un token JWT.

## 📝 Paso 6: Poblar la Base de Datos (Opcional)

Para probar la aplicación con datos de ejemplo, puedes agregar datos manualmente desde la consola de Firebase:

1. Ve a [Firebase Console](https://console.firebase.google.com/)
2. Selecciona tu proyecto
3. Ve a **Firestore Database**
4. Haz clic en **"Iniciar colección"**

### Ejemplo de meditación:

**Colección**: `meditations`

```json
{
  "title": "Meditación para Dormir",
  "description": "Una meditación relajante para conciliar el sueño",
  "audioUrl": "https://ejemplo.com/audio.mp3",
  "duration": 15,
  "category": "sueño",
  "createdAt": "2024-01-01T00:00:00.000Z"
}
```

### Ejemplo de ejercicio de respiración:

**Colección**: `breathing_exercises`

```json
{
  "name": "Respiración 4-7-8",
  "duration": 240,
  "instructions": "Inhala por 4 segundos, sostén por 7, exhala por 8",
  "inhale": 4,
  "hold": 7,
  "exhale": 8
}
```

## 🐛 Solución de Problemas Comunes

### Error: "Cannot find module 'dotenv'"

**Solución**: Ejecuta `npm install`

### Error: "Firebase Admin SDK no inicializado"

**Solución**: 
- Verifica que el archivo `.env` existe
- Verifica que las credenciales de Firebase son correctas
- Asegúrate de que la clave privada incluye `\n` en los saltos de línea

### Error: "Port 8080 already in use"

**Solución**: 
- Cambia el puerto en `.env`: `PORT=3000`
- O cierra el proceso que está usando el puerto 8080

### Error: "CORS blocked"

**Solución**: Agrega el origen de tu aplicación frontend a `ALLOWED_ORIGINS` en `.env`

## ✅ Verificación Final

Si todo está funcionando correctamente, deberías poder:

- ✅ Acceder a `http://localhost:8080` sin errores
- ✅ Ver la documentación en `http://localhost:8080/api/docs`
- ✅ Registrar un usuario con `POST /api/users/register`
- ✅ Iniciar sesión con `POST /api/users/login`
- ✅ Ver las meditaciones con `GET /api/meditations`

## 📚 Próximos Pasos

1. Lee el [README.md](./README.md) para conocer todos los endpoints
2. Explora la documentación en Swagger
3. Integra el backend con tu aplicación móvil Kotlin
4. Agrega más contenido a Firestore (meditaciones, audios, artículos)

## 🆘 ¿Necesitas Ayuda?

Si encuentras algún problema:

1. Revisa los logs del servidor en la consola
2. Verifica que todas las variables de entorno estén configuradas
3. Consulta la documentación de [Firebase Admin SDK](https://firebase.google.com/docs/admin/setup)
4. Contacta al equipo de desarrollo

---

**¡Felicidades! 🎉 Has configurado exitosamente el backend de PeaceNest.**

