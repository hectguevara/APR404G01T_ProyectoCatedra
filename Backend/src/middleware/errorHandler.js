/**
 * Middleware centralizado para el manejo de errores
 * Captura y procesa todos los errores de la aplicación
 */

/**
 * Middleware para manejar rutas no encontradas (404)
 */
const notFound = (req, res, next) => {
  const error = new Error(`Ruta no encontrada - ${req.originalUrl}`);
  res.status(404);
  next(error);
};

/**
 * Middleware para manejar errores generales
 */
const errorHandler = (err, req, res, next) => {
  // Determinar el código de estado
  const statusCode = res.statusCode === 200 ? 500 : res.statusCode;
  
  // Log del error en consola (en producción, usar un logger profesional)
  console.error('❌ Error:', {
    message: err.message,
    stack: process.env.NODE_ENV === 'development' ? err.stack : undefined,
    url: req.originalUrl,
    method: req.method
  });

  // Responder con el error
  res.status(statusCode).json({
    error: err.message || 'Error interno del servidor',
    code: err.code || 'INTERNAL_ERROR',
    ...(process.env.NODE_ENV === 'development' && { stack: err.stack })
  });
};

/**
 * Middleware para validar errores de Express Validator
 */
const handleValidationErrors = (req, res, next) => {
  const { validationResult } = require('express-validator');
  const errors = validationResult(req);
  
  if (!errors.isEmpty()) {
    return res.status(400).json({
      error: 'Error de validación',
      code: 'VALIDATION_ERROR',
      details: errors.array()
    });
  }
  
  next();
};

module.exports = {
  notFound,
  errorHandler,
  handleValidationErrors
};

