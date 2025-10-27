/**
 * Script de ayuda para configurar Firebase
 * Este script te ayudará a crear el archivo .env con las credenciales de Firebase
 * 
 * Uso: node setup-firebase.js [ruta-al-archivo-firebase.json]
 */

const fs = require('fs');
const path = require('path');
const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

function question(query) {
  return new Promise(resolve => rl.question(query, resolve));
}

console.log('');
console.log('╔══════════════════════════════════════════════════════╗');
console.log('║                                                      ║');
console.log('║      🔥 CONFIGURACIÓN DE FIREBASE 🔥                 ║');
console.log('║                                                      ║');
console.log('╚══════════════════════════════════════════════════════╝');
console.log('');

async function setupFirebase() {
  try {
    console.log('Este script te ayudará a configurar Firebase para PeaceNest.\n');

    // Opción 1: Usar archivo JSON
    const useJsonFile = await question('¿Tienes el archivo JSON de Firebase descargado? (s/n): ');
    
    let firebaseConfig = {};

    if (useJsonFile.toLowerCase() === 's' || useJsonFile.toLowerCase() === 'si') {
      const jsonPath = await question('Ingresa la ruta al archivo JSON (o arrastra el archivo aquí): ');
      const cleanPath = jsonPath.trim().replace(/['"]/g, '');
      
      try {
        const jsonContent = fs.readFileSync(cleanPath, 'utf8');
        const serviceAccount = JSON.parse(jsonContent);
        
        firebaseConfig = {
          projectId: serviceAccount.project_id,
          clientEmail: serviceAccount.client_email,
          privateKey: serviceAccount.private_key
        };
        
        console.log('\n✅ Archivo JSON leído correctamente');
      } catch (error) {
        console.error('❌ Error al leer el archivo JSON:', error.message);
        console.log('Por favor, ingresa las credenciales manualmente:\n');
        firebaseConfig = await getManualConfig();
      }
    } else {
      console.log('\nIngresa las credenciales de Firebase manualmente:\n');
      firebaseConfig = await getManualConfig();
    }

    // Generar JWT Secret
    const crypto = require('crypto');
    const jwtSecret = crypto.randomBytes(64).toString('hex');

    // Configuración adicional
    const port = await question('Puerto del servidor (default: 8080): ') || '8080';
    const nodeEnv = await question('Entorno (development/production, default: development): ') || 'development';

    // Crear contenido del .env
    const envContent = `# =======================================
# PEACENEST BACKEND - VARIABLES DE ENTORNO
# =======================================
# Generado automáticamente el ${new Date().toLocaleString()}

# =======================================
# CONFIGURACIÓN DEL SERVIDOR
# =======================================
PORT=${port}
NODE_ENV=${nodeEnv}

# =======================================
# JWT (JSON WEB TOKEN) CONFIGURATION
# =======================================
JWT_SECRET=${jwtSecret}
JWT_EXPIRES_IN=7d

# =======================================
# FIREBASE CONFIGURATION
# =======================================
FIREBASE_PROJECT_ID=${firebaseConfig.projectId}
FIREBASE_CLIENT_EMAIL=${firebaseConfig.clientEmail}
FIREBASE_PRIVATE_KEY="${firebaseConfig.privateKey}"

# =======================================
# CORS (Cross-Origin Resource Sharing)
# =======================================
ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080

# =======================================
# NOTAS
# =======================================
# - NUNCA subas este archivo a Git
# - JWT_SECRET fue generado automáticamente
# - Agrega los orígenes de tu app móvil a ALLOWED_ORIGINS si es necesario
`;

    // Guardar archivo .env
    const envPath = path.join(__dirname, '.env');
    
    if (fs.existsSync(envPath)) {
      const overwrite = await question('\n⚠️  Ya existe un archivo .env. ¿Deseas sobrescribirlo? (s/n): ');
      if (overwrite.toLowerCase() !== 's' && overwrite.toLowerCase() !== 'si') {
        console.log('\n❌ Operación cancelada. No se modificó el archivo .env existente.');
        rl.close();
        return;
      }
      // Hacer backup del .env anterior
      fs.copyFileSync(envPath, `${envPath}.backup.${Date.now()}`);
      console.log('📦 Se creó un backup del archivo .env anterior');
    }

    fs.writeFileSync(envPath, envContent);

    console.log('\n');
    console.log('╔══════════════════════════════════════════════════════╗');
    console.log('║                                                      ║');
    console.log('║          ✅ CONFIGURACIÓN COMPLETADA ✅              ║');
    console.log('║                                                      ║');
    console.log('╚══════════════════════════════════════════════════════╝');
    console.log('');
    console.log('📄 Archivo .env creado exitosamente en:');
    console.log(`   ${envPath}`);
    console.log('');
    console.log('🔐 Credenciales configuradas:');
    console.log(`   - Project ID: ${firebaseConfig.projectId}`);
    console.log(`   - Client Email: ${firebaseConfig.clientEmail}`);
    console.log(`   - JWT Secret: Generado (${jwtSecret.substring(0, 20)}...)`);
    console.log('');
    console.log('🚀 Próximos pasos:');
    console.log('   1. Instala las dependencias: npm install');
    console.log('   2. Inicia el servidor: npm run dev');
    console.log('   3. Verifica en: http://localhost:' + port);
    console.log('');

  } catch (error) {
    console.error('\n❌ Error:', error.message);
  } finally {
    rl.close();
  }
}

async function getManualConfig() {
  const projectId = await question('Firebase Project ID: ');
  const clientEmail = await question('Firebase Client Email: ');
  console.log('\nPega la clave privada completa (incluyendo BEGIN y END):');
  const privateKey = await question('Private Key: ');

  return {
    projectId,
    clientEmail,
    privateKey
  };
}

// Ejecutar el script
setupFirebase();

