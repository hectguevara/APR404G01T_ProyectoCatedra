/**
 * Middleware de autenticación JWT
 * Verifica que el usuario esté autenticado antes de acceder a rutas protegidas
 */

const { verifyToken } = require('../config/jwt');

/**
 * Middleware para verificar el token JWT en las peticiones
 */
const authenticateToken = (req, res, next) => {
  try {
    // Obtener el token del header Authorization
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1]; // Format: "Bearer TOKEN"

    if (!token) {
      return res.status(401).json({
        error: 'Acceso denegado. No se proporcionó token de autenticación.',
        code: 'NO_TOKEN'
      });
    }

    // Verificar el token
    const decoded = verifyToken(token);
    
    // Agregar la información del usuario a la request
    req.user = decoded;
    
    next();
  } catch (error) {
    return res.status(403).json({
      error: 'Token inválido o expirado',
      code: 'INVALID_TOKEN'
    });
  }
};

/**
 * Middleware opcional de autenticación (no bloquea si no hay token)
 */
const optionalAuth = (req, res, next) => {
  try {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];

    if (token) {
      const decoded = verifyToken(token);
      req.user = decoded;
    }
    
    next();
  } catch (error) {
    // Si el token es inválido, simplemente continuamos sin usuario
    next();
  }
};

module.exports = {
  authenticateToken,
  optionalAuth
};

