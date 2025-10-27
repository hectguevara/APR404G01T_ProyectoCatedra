/**
 * Servidor principal
 * Inicializa la aplicación Express y el servidor HTTP
 */

require('dotenv').config();
const app = require('./app');

// Importar configuración de Firebase para inicializarlo
require('./config/firebase');

// Puerto del servidor
const PORT = process.env.PORT || 8080;

// Iniciar servidor
const server = app.listen(PORT, () => {
  console.log('');
  console.log('╔══════════════════════════════════════════════════════╗');
  console.log('║                                                      ║');
  console.log('║              🌿 PEACENEST API 🌿                     ║');
  console.log('║                                                      ║');
  console.log('╚══════════════════════════════════════════════════════╝');
  console.log('');
  console.log(`✅ Servidor ejecutándose en el puerto ${PORT}`);
  console.log(`🌍 Entorno: ${process.env.NODE_ENV || 'development'}`);
  console.log(`📚 Documentación: http://localhost:${PORT}/api/docs`);
  console.log(`🏥 Health Check: http://localhost:${PORT}/health`);
  console.log('');
  console.log('Endpoints disponibles:');
  console.log(`  - POST   /api/users/register`);
  console.log(`  - POST   /api/users/login`);
  console.log(`  - GET    /api/users/profile`);
  console.log(`  - GET    /api/meditations`);
  console.log(`  - GET    /api/breathing`);
  console.log(`  - POST   /api/breathing/progress`);
  console.log(`  - GET    /api/audios`);
  console.log(`  - GET    /api/articles`);
  console.log(`  - POST   /api/tracking`);
  console.log(`  - GET    /api/tracking/stats`);
  console.log(`  - GET    /api/offline/resources`);
  console.log('');
  console.log('Presiona CTRL+C para detener el servidor');
  console.log('');
});

// Manejo de errores no capturados
process.on('unhandledRejection', (err) => {
  console.error('❌ Error no manejado:', err);
  console.log('🔄 Cerrando servidor...');
  server.close(() => {
    process.exit(1);
  });
});

process.on('uncaughtException', (err) => {
  console.error('❌ Excepción no capturada:', err);
  console.log('🔄 Cerrando servidor...');
  server.close(() => {
    process.exit(1);
  });
});

// Manejo de señales de terminación
process.on('SIGTERM', () => {
  console.log('👋 SIGTERM recibido. Cerrando servidor gracefully...');
  server.close(() => {
    console.log('✅ Servidor cerrado correctamente');
    process.exit(0);
  });
});

process.on('SIGINT', () => {
  console.log('\n👋 SIGINT recibido. Cerrando servidor gracefully...');
  server.close(() => {
    console.log('✅ Servidor cerrado correctamente');
    process.exit(0);
  });
});

module.exports = server;

