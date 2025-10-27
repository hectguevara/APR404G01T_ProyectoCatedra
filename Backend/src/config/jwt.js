/**
 * Configuración de JWT (JSON Web Tokens)
 * Proporciona funciones para generar y verificar tokens
 */

const jwt = require('jsonwebtoken');
require('dotenv').config();

const JWT_SECRET = process.env.JWT_SECRET || 'default_secret_key_change_in_production';
const JWT_EXPIRES_IN = process.env.JWT_EXPIRES_IN || '7d';

/**
 * Genera un token JWT para un usuario
 * @param {Object} payload - Datos del usuario a incluir en el token
 * @returns {string} Token JWT generado
 */
const generateToken = (payload) => {
  try {
    return jwt.sign(payload, JWT_SECRET, {
      expiresIn: JWT_EXPIRES_IN
    });
  } catch (error) {
    throw new Error('Error al generar el token: ' + error.message);
  }
};

/**
 * Verifica la validez de un token JWT
 * @param {string} token - Token a verificar
 * @returns {Object} Payload decodificado del token
 */
const verifyToken = (token) => {
  try {
    return jwt.verify(token, JWT_SECRET);
  } catch (error) {
    throw new Error('Token inválido o expirado');
  }
};

module.exports = {
  generateToken,
  verifyToken,
  JWT_SECRET,
  JWT_EXPIRES_IN
};

