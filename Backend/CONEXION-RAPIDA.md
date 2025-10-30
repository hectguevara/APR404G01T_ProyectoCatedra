# 🚀 Guía Rápida de Conexión Backend ↔️ Frontend

## ✅ Pasos Completados

### Frontend (Android - PeaceNest) ✓
1. ✅ Permisos de INTERNET agregados al AndroidManifest
2. ✅ Dependencias de Retrofit configuradas
3. ✅ Servicio de API creado
4. ✅ LoginScreen y RegisterScreen conectados al backend

### Backend (Node.js) ✓
1. ✅ Servidor configurado en puerto 8080
2. ✅ CORS habilitado para aplicaciones móviles
3. ✅ Endpoints de autenticación listos

---

## 📋 Instrucciones de Uso

### 1️⃣ Configurar el Backend

**Opción A: Configuración Rápida (Sin Firebase)**
```bash
cd Backend

# Copiar archivo de ejemplo
copy .env.example .env

# Instalar dependencias
npm install

# Iniciar servidor
npm run dev
```

**Opción B: Configuración con Firebase**
```bash
cd Backend

# Ejecutar script de configuración
node setup-firebase.js

# Seguir las instrucciones en pantalla
```

El servidor estará disponible en: `http://localhost:8080`

### 2️⃣ Ejecutar el Frontend

**Desde Android Studio:**

1. Abre el proyecto `Peacenest` en Android Studio
2. Espera a que sincronice Gradle
3. Inicia un emulador Android o conecta un dispositivo físico
4. Haz clic en Run ▶️

**IMPORTANTE**: La app está configurada para conectarse a:
- `http://10.0.2.2:8080` → localhost desde el emulador
- Si usas dispositivo físico, edita `ApiConfig.kt` con la IP de tu PC

---

## 🔧 Configuración de Red

### Para Emulador Android:
- ✅ Ya está configurado: `10.0.2.2:8080`
- No necesitas cambiar nada

### Para Dispositivo Físico:

1. Encuentra la IP de tu PC:
   ```bash
   # Windows
   ipconfig
   
   # Mac/Linux
   ifconfig
   ```

2. Edita `Peacenest/app/src/main/java/com/example/peacenest/network/ApiConfig.kt`:
   ```kotlin
   const val BASE_URL = "http://TU_IP_AQUI:8080/api/"
   // Ejemplo: "http://192.168.1.100:8080/api/"
   ```

3. Asegúrate de que tu PC y teléfono estén en la misma red WiFi

---

## 🧪 Probar la Conexión

### Método 1: Usar la App

1. Inicia el backend: `npm run dev`
2. Inicia la app en Android Studio
3. Ve a la pantalla de Registro
4. Crea una cuenta de prueba:
   - Nombre: Test User
   - Email: test@peacenest.com
   - Password: 123456
5. Si todo funciona, serás redirigido al Home

### Método 2: Probar Endpoints Manualmente

**Desde tu navegador:**
```
http://localhost:8080/health
http://localhost:8080/api/docs
```

**Con PowerShell:**
```powershell
# Health check
Invoke-WebRequest -Uri "http://localhost:8080/health"

# Registro de usuario
$body = @{
    email = "test@peacenest.com"
    password = "123456"
    name = "Test User"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/users/register" `
    -Method POST `
    -ContentType "application/json" `
    -Body $body
```

---

## 📱 Flujo de Autenticación

```
┌─────────────┐          ┌─────────────┐          ┌─────────────┐
│   Usuario   │          │   Android   │          │   Backend   │
│             │          │     App     │          │   Node.js   │
└──────┬──────┘          └──────┬──────┘          └──────┬──────┘
       │                        │                        │
       │  1. Ingresa datos      │                        │
       ├───────────────────────>│                        │
       │                        │                        │
       │                        │  2. POST /api/users/   │
       │                        │     register o login   │
       │                        ├───────────────────────>│
       │                        │                        │
       │                        │  3. Valida credenciales│
       │                        │     y genera JWT       │
       │                        │<───────────────────────┤
       │                        │                        │
       │  4. Redirige al Home   │                        │
       │<───────────────────────┤                        │
       │                        │                        │
```

---

## 🐛 Solución de Problemas

### ❌ "Error de conexión" en la app

**Problema**: La app no puede conectarse al backend

**Soluciones**:
1. Verifica que el backend esté corriendo: `npm run dev`
2. Revisa que el puerto sea 8080
3. Si usas dispositivo físico, verifica la IP en `ApiConfig.kt`
4. Desactiva temporalmente el firewall de Windows

### ❌ "Firebase error" en el backend

**Problema**: El backend no puede conectarse a Firebase

**Soluciones**:
1. Ejecuta `node setup-firebase.js` para configurar
2. O comenta temporalmente el código de Firebase para pruebas básicas

### ❌ "Network Security Policy" en Android

**Problema**: Android bloquea conexiones HTTP

**Solución**: Ya está configurado con `android:usesCleartextTraffic="true"` en el AndroidManifest

### ❌ El emulador es muy lento

**Soluciones**:
1. Usa un dispositivo físico
2. Habilita la aceleración de hardware en Android Studio
3. Reduce la resolución del emulador

---

## 📊 Estado de Endpoints

| Endpoint | Estado | Requiere Auth |
|----------|--------|---------------|
| POST /api/users/register | ✅ Conectado | No |
| POST /api/users/login | ✅ Conectado | No |
| GET /api/users/profile | ⚠️ Pendiente | Sí |
| GET /api/meditations | ⏳ Por implementar | No |
| GET /api/breathing | ⏳ Por implementar | No |
| GET /api/articles | ⏳ Por implementar | No |

---

## 📝 Próximos Pasos

1. **Configurar Firebase** (si aún no lo has hecho)
2. **Implementar más pantallas** conectadas al backend
3. **Agregar manejo de tokens JWT** para endpoints protegidos
4. **Implementar caché local** para modo offline
5. **Agregar manejo de errores** más robusto

---

## 🎯 Estructura de Archivos Creados

```
Backend/
├── .env.example              # ← Archivo de ejemplo
├── CONEXION-RAPIDA.md        # ← Esta guía
└── src/
    └── ... (ya existente)

Peacenest/
└── app/src/main/java/com/example/peacenest/
    ├── network/
    │   ├── ApiConfig.kt      # ← Configuración de URLs
    │   ├── ApiService.kt     # ← Definición de endpoints
    │   ├── RetrofitClient.kt # ← Cliente HTTP
    │   └── models/
    │       └── AuthModels.kt # ← Modelos de datos
    └── ui/auth/
        ├── LoginScreen.kt    # ← Conectado al backend
        └── RegisterScreen.kt # ← Conectado al backend
```

---

## 💡 Consejos

- **Para desarrollo**: Usa el emulador con `10.0.2.2`
- **Para demos**: Usa dispositivo físico con IP de tu PC
- **Para producción**: Despliega el backend en Heroku/Railway/Render
- **Logs útiles**: El backend muestra todas las peticiones en consola
- **Documentación**: Visita `http://localhost:8080/api/docs`

---

¡Listo! Tu backend y frontend están conectados y funcionando. 🎉

