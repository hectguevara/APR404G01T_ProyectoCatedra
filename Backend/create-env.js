/**
 * Script para crear automáticamente el archivo .env
 * Lee el archivo JSON de Firebase y genera la configuración
 */

const fs = require('fs');
const path = require('path');

console.log('\n🔥 Creando archivo .env desde las credenciales de Firebase...\n');

try {
  // Buscar el archivo JSON de Firebase
  const files = fs.readdirSync(__dirname);
  const firebaseFile = files.find(f => f.includes('firebase-adminsdk') && f.endsWith('.json'));
  
  if (!firebaseFile) {
    console.error('❌ No se encontró el archivo JSON de Firebase.');
    console.log('📝 Asegúrate de que el archivo esté en la carpeta Backend');
    process.exit(1);
  }
  
  console.log(`✅ Archivo encontrado: ${firebaseFile}`);
  
  // Leer el archivo JSON
  const jsonPath = path.join(__dirname, firebaseFile);
  const serviceAccount = JSON.parse(fs.readFileSync(jsonPath, 'utf8'));
  
  // Generar JWT Secret
  const crypto = require('crypto');
  const jwtSecret = crypto.randomBytes(64).toString('hex');
  
  // Crear contenido del .env
  const envContent = `# =======================================
# PEACENEST BACKEND - VARIABLES DE ENTORNO
# =======================================
# Generado automáticamente el ${new Date().toLocaleString()}

# =======================================
# CONFIGURACIÓN DEL SERVIDOR
# =======================================
PORT=8080
NODE_ENV=development

# =======================================
# JWT (JSON WEB TOKEN) CONFIGURATION
# =======================================
JWT_SECRET=${jwtSecret}
JWT_EXPIRES_IN=7d

# =======================================
# FIREBASE CONFIGURATION
# =======================================
FIREBASE_PROJECT_ID=${serviceAccount.project_id}
FIREBASE_CLIENT_EMAIL=${serviceAccount.client_email}
FIREBASE_PRIVATE_KEY="${serviceAccount.private_key}"

# =======================================
# CORS (Cross-Origin Resource Sharing)
# =======================================
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080

# =======================================
# NOTAS IMPORTANTES
# =======================================
# - Este archivo fue generado automáticamente
# - NUNCA subas este archivo a Git
# - Las credenciales fueron extraídas de: ${firebaseFile}
# - JWT_SECRET es único para esta instalación
# - Agrega los orígenes de tu app móvil a ALLOWED_ORIGINS si es necesario
`;

  // Guardar archivo .env
  const envPath = path.join(__dirname, '.env');
  fs.writeFileSync(envPath, envContent);
  
  console.log('\n╔══════════════════════════════════════════════════════╗');
  console.log('║                                                      ║');
  console.log('║          ✅ CONFIGURACIÓN COMPLETADA ✅              ║');
  console.log('║                                                      ║');
  console.log('╚══════════════════════════════════════════════════════╝\n');
  
  console.log('📄 Archivo .env creado exitosamente');
  console.log('');
  console.log('🔐 Credenciales configuradas:');
  console.log(`   - Project ID: ${serviceAccount.project_id}`);
  console.log(`   - Client Email: ${serviceAccount.client_email}`);
  console.log(`   - JWT Secret: Generado (${jwtSecret.substring(0, 20)}...)`);
  console.log('');
  console.log('🚀 Próximos pasos:');
  console.log('   1. Instalar dependencias: npm install');
  console.log('   2. Iniciar servidor: npm run dev');
  console.log('   3. Abrir: http://localhost:8080/api/docs');
  console.log('');
  console.log('⚠️  IMPORTANTE:');
  console.log(`   - Elimina el archivo: ${firebaseFile}`);
  console.log('   - O muévelo fuera del proyecto por seguridad');
  console.log('   - NUNCA subas estos archivos a Git');
  console.log('');
  
} catch (error) {
  console.error('❌ Error:', error.message);
  process.exit(1);
}

