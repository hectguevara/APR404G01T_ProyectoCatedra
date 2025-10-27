/**
 * Configuración de Firebase Admin SDK
 * Inicializa la conexión con Firebase Firestore
 */

const admin = require('firebase-admin');
require('dotenv').config();

// Verificar que las variables de entorno estén configuradas
if (!process.env.FIREBASE_PROJECT_ID || !process.env.FIREBASE_CLIENT_EMAIL || !process.env.FIREBASE_PRIVATE_KEY) {
  console.error('ERROR: Variables de entorno de Firebase no configuradas correctamente');
  process.exit(1);
}

// Configurar Firebase Admin con las credenciales del archivo .env
try {
  admin.initializeApp({
    credential: admin.credential.cert({
      projectId: process.env.FIREBASE_PROJECT_ID,
      clientEmail: process.env.FIREBASE_CLIENT_EMAIL,
      privateKey: process.env.FIREBASE_PRIVATE_KEY.replace(/\\n/g, '\n')
    })
  });

  console.log('✅ Firebase Admin SDK inicializado correctamente');
} catch (error) {
  console.error('❌ Error al inicializar Firebase Admin SDK:', error.message);
  process.exit(1);
}

// Exportar instancias de Firestore y Storage
const db = admin.firestore();
const storage = admin.storage();
const auth = admin.auth();

module.exports = {
  admin,
  db,
  storage,
  auth
};

