/**
 * Validadores de datos usando Express Validator
 * Define las reglas de validación para cada endpoint
 */

const { body, param, query } = require('express-validator');

/**
 * Validadores para usuarios
 */
const userValidators = {
  register: [
    body('email')
      .isEmail()
      .withMessage('Debe proporcionar un email válido')
      .normalizeEmail(),
    body('password')
      .isLength({ min: 6 })
      .withMessage('La contraseña debe tener al menos 6 caracteres'),
    body('name')
      .notEmpty()
      .withMessage('El nombre es obligatorio')
      .trim()
      .isLength({ min: 2, max: 100 })
      .withMessage('El nombre debe tener entre 2 y 100 caracteres')
  ],
  login: [
    body('email')
      .isEmail()
      .withMessage('Debe proporcionar un email válido')
      .normalizeEmail(),
    body('password')
      .notEmpty()
      .withMessage('La contraseña es obligatoria')
  ]
};

/**
 * Validadores para seguimiento emocional
 */
const trackingValidators = {
  create: [
    body('mood')
      .notEmpty()
      .withMessage('El estado de ánimo es obligatorio')
      .isIn(['muy_mal', 'mal', 'neutral', 'bien', 'muy_bien'])
      .withMessage('Estado de ánimo inválido'),
    body('notes')
      .optional()
      .trim()
      .isLength({ max: 500 })
      .withMessage('Las notas no pueden exceder 500 caracteres'),
    body('activities')
      .optional()
      .isArray()
      .withMessage('Las actividades deben ser un array')
  ],
  getStats: [
    query('startDate')
      .optional()
      .isISO8601()
      .withMessage('Fecha de inicio inválida'),
    query('endDate')
      .optional()
      .isISO8601()
      .withMessage('Fecha de fin inválida')
  ]
};

/**
 * Validadores para meditaciones
 */
const meditationValidators = {
  getById: [
    param('id')
      .notEmpty()
      .withMessage('El ID de la meditación es obligatorio')
      .trim()
  ]
};

/**
 * Validadores para artículos
 */
const articleValidators = {
  getById: [
    param('id')
      .notEmpty()
      .withMessage('El ID del artículo es obligatorio')
      .trim()
  ],
  getByCategory: [
    query('category')
      .optional()
      .trim()
  ]
};

/**
 * Validadores para ejercicios de respiración
 */
const breathingValidators = {
  saveProgress: [
    body('exerciseId')
      .notEmpty()
      .withMessage('El ID del ejercicio es obligatorio'),
    body('completed')
      .isBoolean()
      .withMessage('El campo completed debe ser booleano'),
    body('duration')
      .optional()
      .isNumeric()
      .withMessage('La duración debe ser un número')
  ]
};

module.exports = {
  userValidators,
  trackingValidators,
  meditationValidators,
  articleValidators,
  breathingValidators
};

