/**
 * Rutas de Seguimiento Emocional
 * Define los endpoints para el registro y análisis del estado de ánimo
 */

const express = require('express');
const router = express.Router();
const {
  createTracking,
  getUserTracking,
  getStats,
  getTrackingById,
  updateTracking,
  deleteTracking
} = require('../controllers/trackingController');
const { authenticateToken } = require('../middleware/auth');
const { trackingValidators } = require('../middleware/validators');
const { handleValidationErrors } = require('../middleware/errorHandler');

/**
 * @swagger
 * /api/tracking:
 *   post:
 *     tags: [Seguimiento Emocional]
 *     summary: Crear nuevo registro de seguimiento
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             required:
 *               - mood
 *             properties:
 *               mood:
 *                 type: string
 *                 enum: [muy_mal, mal, neutral, bien, muy_bien]
 *               notes:
 *                 type: string
 *               activities:
 *                 type: array
 *                 items:
 *                   type: string
 *     responses:
 *       201:
 *         description: Registro creado exitosamente
 */
router.post(
  '/',
  authenticateToken,
  trackingValidators.create,
  handleValidationErrors,
  createTracking
);

/**
 * @swagger
 * /api/tracking:
 *   get:
 *     tags: [Seguimiento Emocional]
 *     summary: Obtener registros de seguimiento del usuario
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: startDate
 *         schema:
 *           type: string
 *           format: date
 *       - in: query
 *         name: endDate
 *         schema:
 *           type: string
 *           format: date
 *       - in: query
 *         name: limit
 *         schema:
 *           type: number
 *     responses:
 *       200:
 *         description: Lista de registros
 */
router.get('/', authenticateToken, getUserTracking);

/**
 * @swagger
 * /api/tracking/stats:
 *   get:
 *     tags: [Seguimiento Emocional]
 *     summary: Obtener estadísticas del estado de ánimo
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: query
 *         name: startDate
 *         schema:
 *           type: string
 *           format: date
 *       - in: query
 *         name: endDate
 *         schema:
 *           type: string
 *           format: date
 *     responses:
 *       200:
 *         description: Estadísticas del estado de ánimo
 */
router.get(
  '/stats',
  authenticateToken,
  trackingValidators.getStats,
  handleValidationErrors,
  getStats
);

/**
 * @swagger
 * /api/tracking/{id}:
 *   get:
 *     tags: [Seguimiento Emocional]
 *     summary: Obtener un registro específico
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Datos del registro
 *       404:
 *         description: Registro no encontrado
 */
router.get('/:id', authenticateToken, getTrackingById);

/**
 * @swagger
 * /api/tracking/{id}:
 *   put:
 *     tags: [Seguimiento Emocional]
 *     summary: Actualizar un registro
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Registro actualizado
 */
router.put('/:id', authenticateToken, updateTracking);

/**
 * @swagger
 * /api/tracking/{id}:
 *   delete:
 *     tags: [Seguimiento Emocional]
 *     summary: Eliminar un registro
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: id
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Registro eliminado
 */
router.delete('/:id', authenticateToken, deleteTracking);

module.exports = router;

