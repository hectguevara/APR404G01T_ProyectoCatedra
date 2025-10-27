/**
 * Aplicación Express principal
 * Configura middlewares, rutas y documentación
 */

const express = require('express');
const cors = require('cors');
const morgan = require('morgan');
const swaggerUi = require('swagger-ui-express');
require('dotenv').config();

// Importar configuración
const swaggerDefinition = require('./config/swagger');

// Importar middlewares
const { notFound, errorHandler } = require('./middleware/errorHandler');

// Importar rutas
const userRoutes = require('./routes/userRoutes');
const meditationRoutes = require('./routes/meditationRoutes');
const breathingRoutes = require('./routes/breathingRoutes');
const audioRoutes = require('./routes/audioRoutes');
const articleRoutes = require('./routes/articleRoutes');
const trackingRoutes = require('./routes/trackingRoutes');
const offlineRoutes = require('./routes/offlineRoutes');

// Crear aplicación Express
const app = express();

// ========== MIDDLEWARES ==========

// CORS - Configurar orígenes permitidos
const allowedOrigins = process.env.ALLOWED_ORIGINS 
  ? process.env.ALLOWED_ORIGINS.split(',')
  : ['http://localhost:3000', 'http://localhost:8080'];

app.use(cors({
  origin: (origin, callback) => {
    // Permitir requests sin origen (mobile apps, Postman, etc.)
    if (!origin) return callback(null, true);
    
    if (allowedOrigins.includes(origin)) {
      callback(null, true);
    } else {
      callback(new Error('No permitido por CORS'));
    }
  },
  credentials: true
}));

// Parser de JSON y URL-encoded
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Logger HTTP
if (process.env.NODE_ENV === 'development') {
  app.use(morgan('dev'));
} else {
  app.use(morgan('combined'));
}

// ========== RUTAS ==========

// Ruta de bienvenida
app.get('/', (req, res) => {
  res.status(200).json({
    message: 'Bienvenido a PeaceNest API',
    version: '1.0.0',
    documentation: '/api/docs',
    endpoints: {
      users: '/api/users',
      meditations: '/api/meditations',
      breathing: '/api/breathing',
      audios: '/api/audios',
      articles: '/api/articles',
      tracking: '/api/tracking',
      offline: '/api/offline'
    }
  });
});

// Health check
app.get('/health', (req, res) => {
  res.status(200).json({
    status: 'OK',
    timestamp: new Date().toISOString(),
    uptime: process.uptime()
  });
});

// Documentación Swagger
app.use('/api/docs', swaggerUi.serve, swaggerUi.setup(swaggerDefinition, {
  customCss: '.swagger-ui .topbar { display: none }',
  customSiteTitle: 'PeaceNest API Documentation'
}));

// Rutas de la API
app.use('/api/users', userRoutes);
app.use('/api/meditations', meditationRoutes);
app.use('/api/breathing', breathingRoutes);
app.use('/api/audios', audioRoutes);
app.use('/api/articles', articleRoutes);
app.use('/api/tracking', trackingRoutes);
app.use('/api/offline', offlineRoutes);

// ========== MANEJO DE ERRORES ==========

// Manejar rutas no encontradas
app.use(notFound);

// Manejador de errores centralizado
app.use(errorHandler);

module.exports = app;

